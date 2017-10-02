/**
 * Сборщик клиентской части ServiceDesk.
 * Параметры:
 * @param DEBUG {Boolean} (default false) собрать в дебаг-моде. Добавит логи и глобальные переменные.
 * @param SOURCE_MAPS {Boolean} (default false) добавить сорцмапы в сборку.
 * @param HTTP_TIMEOUT {Number} максимальный таймаут для http запросов к бэкэнду. // пока не работает
 * @param API_ADDRESS {String} (default) (нужен только для локального сервера gulp-connect)
 * @param WEB_CONTEXT {String} контекст веб-приложения (нужен только для локального сервера gulp-connect)
 */
"use strict";

const gulp = require('gulp');
const browserify = require('browserify');
const babelify = require('babelify');
const sourcemaps = require('gulp-sourcemaps');
const rimraf = require('gulp-rimraf');
const concat = require('gulp-concat-util');
const plumber = require('gulp-plumber');
const less = require('gulp-less');
const cleanCSS = require('gulp-clean-css');
const stringify = require('stringify');
const source = require('vinyl-source-stream');
const buffer = require('vinyl-buffer');
const replace = require('gulp-replace');
const gulpif = require('gulp-if');
const rename = require('gulp-rename');
const autoprefixer = require('gulp-autoprefixer');

const connect = require('gulp-connect');
const modrewrite = require('connect-modrewrite');


const util = require('gulp-util');
const env = util.env;

/**
 * Обработка env переменных
 */

if (env.DEBUG === undefined) env.DEBUG = false;
if (env.SOURCE_MAPS === undefined) env.SOURCE_MAPS = false;
if (env.API_ADDRESS === undefined) env.API_ADDRESS = "https://localhost:8443";

if (env.WEB_CONTEXT === undefined) env.WEB_CONTEXT = null;


/**
 * Конфиг для сборки.
 */

const config = {
    source : {
        base: "src/main/",
        index: "src/main/index.html",
        mainLess: "src/main/css/style.less",
        img: "src/main/img/",
        fonts: "src/main/fonts/",
        vendorJs: "src/main/lib/",
        mainJS: "src/main/js/servicedesk.js",
        js: "src/main/js/",
        less: "src/main/css"
    },
    dist : {
        js: "build/dist/js/",
        css: "build/dist/css/",
        img: "build/dist/img/",
        base: "build/dist/",
        fonts: "build/dist/css/fonts/"
    },
    replace: {
        "DEBUG": env.DEBUG || false,
        "HTTP_TIMEOUT" : env.HTTP_TIMEOUT || null,
    }
};

/**
 * Конфиг сервера
 */

const webContext = env.WEB_CONTEXT ? "/"+env.WEB_CONTEXT : "";
const serverConfig = {
    https: true,
    root: "build/dist",
    port: 9999,
    livereload: true,
    middleware: () => [modrewrite([
        `^${webContext}/$ ./index.html`,
        `^${webContext}/js/(.*)$ ./js/$1`,
        `^${webContext}/css/(.*)$ ./css/$1`,
        `^${webContext}/img/(.*)$ ./img/$1`,

        `^${webContext}/rest/(.*)$ ${env.API_ADDRESS}${webContext}/rest/$1 [P]`,
    ])]
};

/**
 * Все значение undefined в replace превращает в строку "undefined"
 * Без этого gulp-replace при попытке подменить что-либо на undefined ставит запятую, и код ломается
 */
for (const key in config.replace) {
    if (config.replace[key] === undefined) config.replace[key] = "undefined"
}

/**
 * Собирает js приложение в app.min.js
 */
gulp.task('build:js', function buildJS() {
    let stream = browserify({entries: config.source.mainJS, debug: env.DEBUG||false }) // Используем браузерификацию на основном js файле
        .transform(babelify, { // Пропускаем через компилятор babel. Он приведет все в ES5
            presets: ["es2015", "stage-0"],
            plugins: ["transform-decorators-legacy", "transform-es2015-for-of"], // Подключаем декораторы
            only: /src\/.*/,
            ignore: /\.min\.js/
        })
        .transform(stringify, ['.html']) // Делаем, чтобы работало import .html
        .bundle()
        .on('error', handleBuildError)
        .pipe(source('app.min.js'))
        .pipe(buffer());

    // Заеняем в коде все слова из списка config.replace
    for (let name in config.replace) {
        let replaceName = "'GULP_REPLACE:"+name+"'";
        const value = config.replace[name];
        stream = stream.pipe( replace(replaceName, value) );
        replaceName = 'GULP_REPLACE:'+name;
        stream = stream.pipe( replace(replaceName, value) );
    }

    return stream
        .pipe( gulpif( env.SOURCE_MAPS, sourcemaps.init({loadMaps: true}) ) ) // сорц-мапы
        .pipe( gulpif( env.SOURCE_MAPS, sourcemaps.write('./') ) )
        .pipe(gulp.dest(config.dist.js)); // кладем все в dest
});


/**
 * Собирает js библиотеки в vendor.js
 */
gulp.task('build:js-vendor', function buildJSVendor() { // Собираем js библиотеки в отдельный файл
    const files = [
        'node_modules/babel-polyfill/dist/polyfill.min.js',
        'node_modules/jquery/dist/jquery.min.js',
        'node_modules/bootstrap/dist/js/bootstrap.min.js',
        'node_modules/angular/angular.min.js',
        'node_modules/angular-sanitize/angular-sanitize.min.js',
        'node_modules/angular-messages/angular-messages.min.js',
        'node_modules/angular-translate/dist/angular-translate.min.js',
        'node_modules/@uirouter/core/_bundles/ui-router-core.min.js', // Новый ui-router не запускается без core
        'node_modules/@uirouter/angularjs/release/ui-router-angularjs.min.js',
        'node_modules/angular-bootstrap-npm/dist/angular-bootstrap.min.js',
        'node_modules/angular-bootstrap-npm/dist/angular-bootstrap-tpls.min.js',
        'node_modules/angular-ui-grid/ui-grid.min.js',
        'node_modules/angular-animate/angular-animate.min.js',
        'node_modules/ui-select/dist/select.min.js',
        'node_modules/moment/moment.js',
        'node_modules/angular-moment/angular-moment.min.js',
        'node_modules/moment/locale/ru.js',
        'node_modules/pdfmake/build/pdfmake.min.js',
        'node_modules/pdfmake/build/vfs_fonts.js',
        'node_modules/angular-bootstrap-datetimepicker/src/js/datetimepicker.js',
        'node_modules/angular-bootstrap-datetimepicker/src/js/datetimepicker.templates.js',
    ];
    return gulp.src(files)
        .pipe(plumber())
        .pipe(concat("vendor.min.js")) // "Склеиваем" минифицированные js в один файл
        .pipe(gulp.dest(config.dist.js)); // Кладем в dest/js

});

/**
 * Собирает less
 */
gulp.task('build:less', function buildLess(){
    return gulp.src(config.source.mainLess)
        .on('error', handleBuildError)
        .pipe(less())
        .pipe(autoprefixer())
        .pipe(sourcemaps.init({loadMaps: true}))
        .pipe(rename('style.min.css'))
        .pipe(cleanCSS({compatibility: 'ie8'}))
        .pipe(sourcemaps.write('./'))
        .pipe(gulp.dest(config.dist.css));
});

/**
 * Собирает less
 */
gulp.task('build:less-vendor', function buildLess(){
    const files = [
        'node_modules/ui-select/dist/select.min.css',
        'node_modules/angular-bootstrap-datetimepicker/src/css/datetimepicker.css'
    ];
    return gulp.src(files)
        .pipe(plumber())
        .pipe(concat("vendor.min.css")) // "Склеиваем" минифицированные js в один файл
        .pipe(gulp.dest(config.dist.css)); // Кладем в dest/js
});

/**
 * Копирует все изображения
 */
gulp.task("copy:img", function copyStatic() {
    return gulp.src(config.source.img+"*")
        .pipe(gulp.dest(config.dist.img));
});

/**
 * Копирует все шрифты
 */
gulp.task("copy:fonts", function copyStatic() {
    return gulp.src(config.source.fonts+"*/**")
        .pipe(gulp.dest(config.dist.fonts));
});

/**
 * Копирует index.html
 */
gulp.task("copy:index", function copyIndex() {
    return gulp.src(config.source.index)
        .pipe(gulp.dest(config.dist.base))
});

/**
 * Обработка ошибок
 */
function handleBuildError(error){
    console.log("Build failed");
    console.log(error.toString());

    this.emit("end");
}

/** Таски, которые следят за изменением файлов проекта
 *  и компилируют, если есть изменения  */

gulp.task('watch:less',function doWatchLess(){
    return gulp.watch(config.source.less+"**/*",gulp.series('build:less','webserver:reload'));
});

gulp.task('watch:static',function doWatchImages(){
    gulp.watch(config.source.index,gulp.series('copy:index', 'webserver:reload'));
    gulp.watch(config.source.img+"**/*",gulp.series('copy:img', 'webserver:reload'));
    return gulp.watch(config.source.fonts+"**/*",gulp.series('copy:fonts',"webserver:reload"))
});

gulp.task('watch:js',function doWatchJs(){
    return gulp.watch(config.source.js+"**/*",gulp.series('build:js','webserver:reload'));
});

/**
 * Такси вебсервера
 */
gulp.task('webserver:start', function () {
    return connect.server(serverConfig)
});

gulp.task('webserver:reload', function () {
    return gulp.src("./")
        .pipe(connect.reload());
});

/**
 * Таски, комбинирующие другие
 */
gulp.task('copy', gulp.parallel('copy:img','copy:index','copy:fonts'));

gulp.task('build', gulp.parallel('build:js','build:less','build:js-vendor', 'build:less-vendor','copy'));

gulp.task('watch', gulp.series('build', gulp.parallel('watch:js','watch:less','watch:static') ) );

gulp.task('default', gulp.series('build') );
