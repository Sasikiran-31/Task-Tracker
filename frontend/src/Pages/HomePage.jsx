import {Button} from "@mui/material";


const HomePage = () => {
  const token = localStorage.getItem('JWT');


  const fetchAll = async () => {
    try{
      const response = await fetch('http://localhost:8081/api/users', {
        method : 'GET',
        headers : {
          'Authorization' : `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });

      const result = await response.text();
      console.log(result);
    } catch (error) {
      console.log(error);
    }



  }
  return (
      <Button
      variant="contained"
      color="primary"
      onClick={fetchAll}>
        Fetch All tasks
      </Button>
  )
}

export default HomePage;
