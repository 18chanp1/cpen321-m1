var express = require('express');
var router = express.Router();

/* GET server ip. */
router.get('/', function(req, res, next) {
    let time = new Date(Date.now());
    let timeString = time.getHours() + ":" + time.getMinutes() + ":" + time.getSeconds()
    res.status(200).send(timeString);
});

module.exports = router;