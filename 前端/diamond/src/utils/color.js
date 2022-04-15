function get_color() {
  return ["#000000", "#00FFFF", "#7FFFD4", "#0000FF", "#8A2BE2", "#A52A2A", "#DEB887", "#5F9EA0", "#7FFF00", "#D2691E", "#6495ED", "#DC143C", "#00FFFF", "#00008B", "#008B8B", "#B8860B", "#A9A9A9", "#006400", "#BDB76B", "#8B008B", "#556B2F", "#9932CC", "#8B0000", "#E9967A", "#8FBC8F", "#483D8B", "#2F4F4F", "#00CED1", "#9400D3", "#00BFFF", "#696969", "#1E90FF", "#D19275", "#B22222", "#228B22", "#DAA520", "#808080", "#008000", "#CD5C5C", "#4B0082", "#7CFC00", "#ADD8E6", "#D3D3D3", "#90EE90", "#20B2AA", "#87CEFA", "#8470FF", "#778899", "#B0C4DE", "#00FF00", "#32CD32", "#800000", "#66CDAA", "#0000CD", "#BA55D3", "#9370D8", "#3CB371", "#7B68EE", "#00FA9A", "#48D1CC", "#C71585", "#191970", "#000080", "#808000", "#6B8E23", "#DA70D6", "#98FB98", "#D87093", "#CD853F", "#DDA0DD", "#B0E0E6", "#800080", "#BC8F8F", "#4169E1", "#8B4513", "#2E8B57", "#A0522D", "#C0C0C0", "#87CEEB", "#6A5ACD", "#708090", "#00FF7F", "#4682B4", "#D2B48C", "#008080", "#D8BFD8", "#40E0D0", "#EE82EE", "#D02090", "#9ACD32"];
}


function get_random(num) {
  const set = new Array();
  for (let i = 1; i <= num; i++) {
    let number1 = parseInt(Math.random() * (get_color().length + 1), 10);
    set.push(number1);
  }
  return set;
}

export {
  get_color,
  get_random
}


