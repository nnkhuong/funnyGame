
var express = require('express');
var path = require('path');

var qrImage = require('qr-image');
var fs = require('fs');

var app = express();

// configure app

app.set('view engine', 'ejs');
app.set('views' , path.join(__dirname, 'views'));
app.use(express.static("public"));
// use middleware


// define routes

var imageQR_File_Name = "";
var locationQR_File = "public/MyQRCode.png";

app.get('/', function (req, res) {
  res.render('index', {imageQR : imageQR_File_Name});
});

app.post('/generateQR', function(req, res) {
	var rString = randomString(20, '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ');
	imageQR_File_Name = "MyQRCode.png";
	qrImage.image(rString, {type:'png', size:20}).pipe(fs.createWriteStream(locationQR_File));
	 
	res.redirect('/');
});

app.post('/getGelocation', function(req, res) {   
  res.render('map');
});

app.post('/doAuthorization', function(req, res) {
  console.log('Received from Client Device');
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
