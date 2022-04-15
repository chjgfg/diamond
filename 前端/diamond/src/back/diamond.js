function start() {
  let exec = require('child_process').exec;
  let exec_path = "java -jar diamond.jar ";
  exec(exec_path, function (error, stdout, stderr) {
    if (error) {
      console.error(error);
      return;
    }
    console.log(stdout);
  });
}

export {
  start
}
