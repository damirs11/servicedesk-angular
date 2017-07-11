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
const uglifyify = require('uglifyify');
const source = require('vinyl-source-stream');
const buffer = require('vinyl-buffer');
const rename = require('gulp-rename');
const autoprefixer = require('gulp-autoprefixer');


const util = require('gulp-util');
const env = util.env;

/**
 * Сборка app.min.js
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
    }
};

/**
 * Собирает js приложение в app.min.js
 */
gulp.task('build:js', function buildJS() {
    return browserify({entries: config.source.mainJS, debug: true }) // Используем браузерификацию на основном js файле
        .transform(babelify, { // Пропускаем через компилятор babel. Он приведет все в ES5
            presets: ["es2015", "stage-0"],
            plugins: ["transform-decorators-legacy"], // Подключаем декораторы
            only: /src\/.*/,
            ignore: /\.min\.js/
        })
        .transform(stringify, ['.html']) // Делаем, чтобы работало import .html
        .bundle()
        .on('error', handleBuildError)
        .pipe(source('app.min.js'))
        .pipe(buffer())
        .pipe(sourcemaps.init({loadMaps: true})) // сорц-мапы
        .pipe(sourcemaps.write('./'))
        .pipe(gulp.dest(config.dist.js)); // кладем все в dest
});


/**
 * Собирает js библиотеки в vendor.js
 */
gulp.task('build:js-vendor', function buildJSVendor() { // Собираем js библиотеки в отдельный файл
    var files = [
        'node_modules/babel-polyfill/browser.js',
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
        .pipe(gulp.dest(config.dist.css))
});

/**
 * Копирует все изображения
 */
gulp.task("copy:img", function copyStatic() {
    return gulp.src(config.source.img+"*")
        .pipe(gulp.dest(config.dist.img))
});

/**
 * Копирует все шрифты
 */
gulp.task("copy:fonts", function copyStatic() {
    return gulp.src(config.source.fonts+"*")
        .pipe(gulp.dest(config.dist.fonts))
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
 *  и компилируют, если есть изменения */

gulp.task('watch:less',gulp.series('build:less',function doWatchLess(){
    return gulp.watch(config.source.less+"**/*",gulp.series('build:less'));
}));

gulp.task('watch:static',gulp.series('copy:index','copy:img','copy:fonts',function doWatchImages(){
    gulp.watch(config.source.index,gulp.series('copy:index'));
    gulp.watch(config.source.img+"**/*",gulp.series('copy:img'));
    return gulp.watch(config.source.fonts+"**/*",gulp.series('copy:fonts'))
}));

gulp.task('watch:js',gulp.series('build:js',function doWatchJs(){
    return gulp.watch(config.source.js+"**/*",gulp.series('build:js'));
}));

gulp.task('watch',gulp.parallel('watch:js','watch:less','watch:static'));

gulp.task('copy', gulp.parallel('copy:img','copy:index','copy:fonts'));

gulp.task('build',gulp.parallel('build:js','build:less','build:js-vendor','copy'));

gulp.task('default',gulp.series('build'));