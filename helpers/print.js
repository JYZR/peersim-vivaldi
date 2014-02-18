var print_stdout = function(data) {
    process.stdout.write(data);
}

var print_stderr = function(data) {
    process.stderr.write(data);
}

module.exports = {
    stdout: print_stdout,
    stderr: print_stderr
}