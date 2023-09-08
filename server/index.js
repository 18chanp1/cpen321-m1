let express = require("express");
let app = express();

app.use(express.json());

// const {MongoClient} = require("mongodb");
// const uri = "mongodb://localhost:27017";
// const client = new MongoClient(uri);

let server = app.listen(80, (req, res) =>
{
    let host = server.address().address;
    let port = server.address().port;
    console.log("Example server running at http://%s:%s", host, port);

})

app.get("/", (req, res) =>
{
    res.status(200).send("Hello COMRADE!");
});

app.get("/server-ip", (req, res) =>
{
    let myIP = "20.163.101.103";
    res.status(200).send(myIP);
})

app.get("/time", (req, res) =>{
    let time = new Date(Date.now());
    let timeString = time.getHours() + ":" + time.getMinutes() + ":" + time.getSeconds()
    res.status(200).send(timeString);
})

app.get("/dev-name", (req, res) =>
{
    let devName = "Paco Chan"
    res.status(200).send(devName);
})

// app.post("/", (req, res) =>
// {
//     res.send(req.body.text);

// })



// app.post("/todolist", async (req, res) =>
// {
//     try {
//         await client.db("test").collection("todolist").insertOne(req.body);
//         res.status(200).send("Todo added successfully\n");
//     } catch(err)
//     {
//         console.log(err);
//         res.send(400).send(err);
//     }
// })

// app.put("/todolist", async(req, res) =>
// {
//     try{
//         await client.db("test").collection("todolist").replaceOne({"task": "finish this tutorial"}, req.body);
//         res.status(200).send("todo item modified successfully");
//     }
//     catch(err)
//     {
//         console.log(err);
//         res.status(400).send(err);
//     }
// } );

// app.delete("/todolist", async(req, res) =>
// {
//     try{
//         await client.db("test").collection("todolist").deleteOne({"task": req.body.task})
//         res.send("todo item deleted unsuccessfully\n")
//     } catch (err)
//     {
//         console.log(err)
//         res.status(400).send(err)
//     }
// })

// app.get("/todolist", async (req, res) =>
// {
//     try{
//         let result = await client.db("test").collection("todolist").find(req.body)
//         await result.forEach(console.dir)
//         res.send("todo item retrieved \n")
//     }
//     catch(err)
//     {
//         console.log(err)
//         res.status(400).send(err)
//     }
// })

// async function run()
// {
//     try{
//         await client.connect();
//         console.log("Successfully connected to DB")
//     } catch(err)
//     {
//         console.error(err);
//         await client.close();
//     }
// }

// run();