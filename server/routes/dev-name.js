var express = require('express');
var router = express.Router();

/* GET dev-name. */
router.get('/', function(req, res, next) {
    res.status(200).send("Paco Chan");
});

module.exports = router;