const express = require('express');
const app = express();

app.get('/percentage', (req, res) => {
  const randomPercentage = parseFloat(Math.random().toFixed(2));
  res.json({ percentage: randomPercentage });
});

app.listen(8080, () => {
  console.log('Mock percentage service running on port 8080');
});