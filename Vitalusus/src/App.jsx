import { useEffect, useState } from "react";
import "./App.css";
import Tabela from "../Tabela";


function App() {
  const [usuarios, setUsuarios] = useState([])
  useEffect(()=>{
    fetch('http://localhost:8080/vitalusus/usuario/findAll')
    .then(retorno => retorno.json())
    .then(retornoJson => setUsuarios(retornoJson))
  }
)
  return(
   <div className="App">
    <Tabela vetor = {usuarios}/>
   </div>
  )
}

export default App;
