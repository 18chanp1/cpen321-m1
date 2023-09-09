var express = require('express');
var router = express.Router();

/* GET server ip. */
router.get('/', function(req, res, next) {
  res.send("40.112.243.121")
});

module.exports = router;