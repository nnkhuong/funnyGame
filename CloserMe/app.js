
var express = require('express');
var path = require('path');

var qrImage = require('qr-image');
var fs = require('fs');
var bodyParser = require('body-parser');
var app = express();

// configure app

app.set('view engine', 'ejs');
app.set('views' , path.join(__dirname, 'views'));
app.use(express.static("public"));
app.use(bodyParser.json());

// use middleware


// define routes

var imageQR_File_Name = "";
var locationQR_File = "public/MyQRCode.png";
var rString = "abc";
var gLongitude = "";
var gLatitude = "";

app.get('/', function (req, res) {
  res.render('index', {imageQR : imageQR_File_Name});
});

app.post('/generateQR', function(req, res) {
	rString = randomString(20, '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ');
	imageQR_File_Name = "MyQRCode.png";
	qrImage.image(rString, {type:'png', size:20}).pipe(fs.createWriteStream(locationQR_File));
	 
	res.redirect('/');
});

app.post('/getGelocation', function(req, res) {   
  res.render('map');
});

//         doAuthorization
app.post('/doAuthorization', function(req, res) {
  console.log('Received from Client Device');
  var qrString = req.body;
  //var lat = req.body.latitude;
  //var lon = req.body.longitude;

  console.log(qrString);

  // if(qrString == rString)
  // {
  //   gLongitude = lon;
  //   gLatitude = lat;
  //   res.send({ status: 200 });
  // }
  // else
  // {
  //   res.send({ status: 401 });
  // }

  res.send({ status: 200 });
});

function randomString(length, chars) {
    var result = '';
    for (var i = length; i > 0; --i) result += chars[Math.floor(Math.random() * chars.length)];
    return result;
}

var server = app.listen(3000, function () {
  var host = server.address().address;
  var port = server.address().port;

  console.log('Example app listening at http://%s:%s', host, port);

});
