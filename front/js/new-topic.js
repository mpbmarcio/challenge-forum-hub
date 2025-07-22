const token = localStorage.getItem("token");

if (!token) {
  alert("Você precisa estar logado.");
  window.location.href = "login.html";
}

carregarCursos();

async function carregarCursos() {
  try {
    const resposta = await fetch("http://localhost:8080/cursos", {
      headers: { Authorization: `Bearer ${token}` }
    });

    if (!resposta.ok) throw new Error("Erro ao carregar cursos.");

    const dados = await resposta.json();
    const cursos = dados.content || dados;

    const select = document.getElementById("cursoSelect");
    cursos.forEach(curso => {
      const option = document.createElement("option");
      option.value = curso.id;            // ✅ ID será usado na requisição
      option.textContent = curso.nome;
      select.appendChild(option);
    });

  } catch (erro) {
    console.error("Erro ao carregar cursos:", erro);
    document.getElementById("mensagemErro").textContent = "Não foi possível carregar os cursos.";
  }
}

document.getElementById("formTopico").addEventListener("submit", async (e) => {
  e.preventDefault();

  const titulo = document.getElementById("titulo").value;
  const mensagem = document.getElementById("mensagem").value;

  const selectCurso = document.getElementById("cursoSelect");
  const cursoId = parseInt(selectCurso.value);                                // ✅ ID válido
  const nomeCurso = selectCurso.options[selectCurso.selectedIndex].text;

  const autorId = localStorage.getItem("usuarioId");

  if (!autorId) {
    alert("Não foi possível identificar o autor. Faça login novamente.");
    window.location.href = "login.html";
    return;
  }

  const status = "NAO_RESPONDIDO";
  const dataInc = new Date().toISOString();

  const corpo = {
    titulo,
    mensagem,
    curso: {
      id: cursoId,
      nome: nomeCurso
    },
    autor: {
      id: parseInt(autorId)
    },
    status,
    dataInc
  };

  console.log("🔍 Corpo da requisição:", corpo);

  try {
    const resposta = await fetch("http://localhost:8080/topicos", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`
      },
      body: JSON.stringify(corpo)
    });

    if (!resposta.ok) throw new Error("Erro ao criar tópico.");

    alert("Tópico criado com sucesso!");
    window.location.href = "index.html";

  } catch (erro) {
    console.error("Erro ao criar tópico:", erro);
    document.getElementById("mensagemErro").textContent = "Não foi possível criar o tópico.";
  }
});