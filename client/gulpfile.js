"use strict";

const gulp = require('gulp');
const browserify = require('browserify');
const babelify = require('babelify');
const sourcemaps = require('gulp-sourcemaps');
const rimraf = require('gulp-rimraf');
const concat = require('gulp-concat-util');
const plumber = require('gulp-plumber');
const less = require('gulp-less');
const minifyCss = require('gulp-minify-css');
const stringify = require('stringify');
const uglifyify = require('uglifyify');
const source = require('vinyl-source-stream');
const buffer = require('vinyl-buffer');
const rename = require('gulp-rename');

const util = require('gulp-util');
const env = util.env;

/**
 * Сборка app.min.js
 */

const config = {
    source : {
        base: "./src/main/",
        index: "./src/main/index.html",
        mainLess: "./src/main/css/style.less",
        img: "./src/main/img/",
        vendorJs: "./src/main/lib/",
        mainJS: "./src/main/js/app/modules/servicedesk/servicedesk.js"
    },
    dist : {
        js: "./build/dist/js/",
        css: "./build/dist/css/",
        img: "./build/dist/img/",
        base: "./build/dist/"
    }
};

/**
 * Собирает js приложение в app.min.js
 */
gulp.task('build:js', function buildJS() {
    var stream = browserify({entries: config.source.mainJS, debug: true }) // Используем браузерификацию на основном js файле
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
        .pipe(plumber())
        .pipe(sourcemaps.init({loadMaps: true})) // сорц-мапы
        .pipe(sourcemaps.write('./'))
        .pipe(gulp.dest(config.dist.js)); // кладем все в dest
    return stream;
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
        'node_modules/angular-ui-router/release/angular-ui-router.min.js',
        'node_modules/angular-bootstrap-npm/dist/angular-bootstrap.min.js',
        'node_modules/angular-bootstrap-npm/dist/angular-bootstrap-tpls.min.js',
        'node_modules/angular-ui-grid/ui-grid.min.js'
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
        .pipe(less())
        .pipe(sourcemaps.init({loadMaps: true}))
        .pipe(rename('style.min.css'))
        .pipe(minifyCss())
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
 * Копирует index.html
 */
gulp.task("copy:index", function copyIndex() {
    return gulp.src(config.source.index)
        .pipe(gulp.dest(config.dist.base))
});


/**
 * Обработка ошибок build:js
 */
function handleBuildError(e){
    console.log("Build error.",e)
}

gulp.task('copy', gulp.parallel('copy:img','copy:index'));

gulp.task('build',gulp.parallel('build:js','build:less','build:js-vendor','copy'));

gulp.task('default',gulp.series('build'));