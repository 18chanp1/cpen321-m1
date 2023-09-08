var express = require('express');
var router = express.Router();

/* GET server ip. */
router.get('/', function(req, res, next) {
  res.send("20.40.202.3")
});

module.exports = router;