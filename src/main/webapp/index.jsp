<!DOCTYPE html>
<html>
<head>
  <title>Room Reservation System</title>
</head>
<body>
<h1>Add New User</h1>
<form method="post" action="app">
  <input type="hidden" name="action" value="addUser">
  Username: <input type="text" name="username"><br>
  Password: <input type="password" name="password"><br>
  Role:
  <select name="role">
    <option value="admin">Admin</option>
    <option value="client">Client</option>
  </select><br>
  <input type="submit" value="Add User">
</form>

<h1>Add New Room</h1>
<form method="post" action="app">
  <input type="hidden" name="action" value="addRoom">
  Name: <input type="text" name="name"><br>
  Capacity: <input type="number" name="capacity"><br>
  Equipment: <input type="text" name="equipment"><br>
  <input type="submit" value="Add Room">
</form>

<h1>Add New Reservation</h1>
<form method="post" action="app">
  <input type="hidden" name="action" value="addReservation">
  User ID: <input type="number" name="userId"><br>
  Room ID: <input type="number" name="roomId"><br>
  Date: <input type="text" name="date" placeholder="yyyy-MM-dd"><br>
  Status: <input type="text" name="status"><br>
  <input type="submit" value="Add Reservation">
</form>
</body>
</html>
