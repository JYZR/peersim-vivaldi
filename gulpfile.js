var gulp = require('gulp');
var gutil = require('gulp-util');
var exec = require('child_process').exec;
var print = require('./helpers/print');

gulp.task('execute', function() {
    // Build Java command
    var classpath = [];
    classpath.push('bin')
    classpath.push('../peersim-1.0.5/peersim-1.0.5.jar')
    classpath.push('../peersim-1.0.5/djep-1.0.0.jar')
    classpath.push('../peersim-1.0.5/jep-2.3.0.jar')
    var cp_arg = ' -cp ' + classpath.join(':');
    var class_arg = 'peersim.Simulator';
    var input_arg = 'vivaldi.conf';

    // Execute Java command
    var proc = exec('java' + [cp_arg, class_arg, input_arg].join(' '), {
        maxBuffer: 1024 * 1024
    }, function(error, stdout, stderr) {
        if (error !== null) {
            print.stderr('exec error: ' + error);
        }
    });
    proc.stdout.on('data', print.stdout);
    proc.stderr.on('data', print.stderr);
});

// Default task
gulp.task('default', ['execute']);