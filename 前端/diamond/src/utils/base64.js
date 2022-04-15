function encode(pass) {
  return btoa(pass);
}


function decode(pass) {
  return atob(pass);
}

export {
  encode,
  decode
}
