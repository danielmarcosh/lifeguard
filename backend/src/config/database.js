const { Client } = require("pg");
const dotenv = require("dotenv");

dotenv.config();
const connectionString = process.env.DATABASE_URL;

const db = new Client({ connectionString });
db.connect();
module.exports = {
  db,
};
