var express = require('express');
var router = express.Router();

/* GET server ip. */
router.get('/', function(req, res, next) {
    let time = new Date(Date.now());
    let hours = time.getHours() < 10 ? "0" + time.getHours() : time.getHours();
    let minutes = time.getMinutes() < 10 ? "0" + time.getMinutes() : time.getMinutes(); 
    let seconds = time.getSeconds() < 10 ? "0" + time.getSeconds() : time.getSeconds(); 
    let timeString = hours + ":" + minutes + ":" + time.getSeconds()
    res.status(200).send(timeString);
});

module.exports = router;