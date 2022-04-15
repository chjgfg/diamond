// import axios from "axios";
import request from "./request";
//
// function f() {
//   var object = {
//     city: "",
//     city_geoname_id: "",
//     continent: "",
//     continent_code: "",
//     continent_geoname_id: "",
//     country: "",
//     country_code: "",
//     country_geoname_id: "",
//     country_is_eu: "",
//     ip_address: "",
//     latitude: "",
//     longitude: "",
//     region: "",
//     region_geoname_id: "",
//     region_iso_code: ""
//   };
//   let apiKey = '1be9a6884abd4c3ea143b59ca317c6b2';
//   axios.get('https://ipgeolocation.abstractapi.com/v1/?api_key=' + apiKey).then((res) => {
//     let parse = JSON.parse(res.request.response);
//     object.city = parse.city;
//     object.city_geoname_id = parse.city_geoname_id;
//     object.continent = parse.continent;
//     object.continent_code = parse.continent_code;
//     object.continent_geoname_id = parse.continent_geoname_id;
//     object.country = parse.country;
//     object.country_code = parse.country_code;
//     object.country_geoname_id = parse.country_geoname_id;
//     object.country_is_eu = parse.country_is_eu;
//     object.ip_address = parse.ip_address;
//     object.latitude = parse.latitude;
//     object.longitude = parse.longitude;
//     object.region = parse.region;
//     object.region_geoname_id = parse.region_geoname_id;
//     object.region_iso_code = parse.region_iso_code;
//     // console.log(JSON.parse(res.request.response));
//     console.log(object);
//   }).catch((err) => {
//     console.log(err);
//   })
// }

function f1() {
  // request({url:'https://ipgeolocation.abstractapi.com/v1/?api_key=1be9a6884abd4c3ea143b59ca317c6b2',type:"get",data:""}).then((res) => {
  request({url:'https://ip-api.io/json?api_key=at_5PARQAcdy3falkirfuP2rYAU7hDL9',type:"post",data:""}).then((res) => {
    // console.log(JSON.parse(res.request.response));
    console.log(res);
  }).catch((err) => {
    console.log(err);
  })

}



export {f1}
