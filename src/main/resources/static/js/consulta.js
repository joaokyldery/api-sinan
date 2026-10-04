const formFiltros =
    document.getElementById("formFiltros");

const filtroAgravo =
    document.getElementById("filtroAgravo");

const filtroNome =
    document.getElementById("filtroNome");

const filtroDuplicadas =
    document.getElementById("filtroDuplicadas");

const btnLimpar =
    document.getElementById("btnLimpar");

const tabela =
    document.getElementById("tabelaNotificacoes");

const contador =
    document.getElementById("contador");

const estadoVazio =
    document.getElementById("estadoVazio");

const mensagem =
    document.getElementById("mensagem");


function exibirMensagem(texto, tipo) {

    mensagem.textContent = texto;

    mensagem.className =
        "alert " + tipo;
}


function esconderMensagem() {

    mensagem.className =
        "alert hidden";

    mensagem.textContent = "";
}


function formatarData(data) {

    if (!data) {
        return "-";
    }

    const partes =
        data.split("-");

    if (partes.length !== 3) {
        return data;
    }

    return partes[2]
        + "/"
        + partes[1]
        + "/"
        + partes[0];
}


function criarCelula(texto) {

    const td =
        document.createElement("td");

    td.textContent =
        texto ?? "-";

    return td;
}


function criarLinha(notificacao) {

    const tr =
        document.createElement("tr");

    tr.appendChild(
        criarCelula(
            notificacao.id
        )
    );

    tr.appendChild(
        criarCelula(
            notificacao.agravo
        )
    );

    tr.appendChild(
        criarCelula(
            notificacao.nomePaciente
        )
    );

    tr.appendChild(
        criarCelula(
            formatarData(
                notificacao.dataNotificacao
            )
        )
    );

    tr.appendChild(
        criarCelula(
            formatarData(
                notificacao.dataNascimento
            )
        )
    );

    const residencia =
        [
            notificacao.municipioResidencia,
            notificacao.ufResidencia
        ]
        .filter(Boolean)
        .join(" / ")
        || notificacao.paisResidencia
        || "-";

    tr.appendChild(
        criarCelula(residencia)
    );

    const tdAcoes =
        document.createElement("td");

    const acoes =
        document.createElement("div");

    acoes.className =
        "actions";

    const editar =
        document.createElement("a");

    editar.className =
        "button secondary small";

    editar.textContent =
        "Editar";

    editar.href =
        "/cadastro.html?id="
        + notificacao.id;

    const excluir =
        document.createElement("button");

    excluir.className =
        "button danger small";

    excluir.type =
        "button";

    excluir.textContent =
        "Excluir";

    excluir.addEventListener(
        "click",
        function () {

            excluirRegistro(
                notificacao
            );
        }
    );

    acoes.appendChild(editar);
    acoes.appendChild(excluir);

    tdAcoes.appendChild(acoes);

    tr.appendChild(tdAcoes);

    return tr;
}


function renderizarTabela(notificacoes) {

    tabela.innerHTML = "";

    contador.textContent =
        notificacoes.length
        + " registro(s) encontrado(s)";

    if (notificacoes.length === 0) {

        estadoVazio.classList.remove(
            "hidden"
        );

        return;
    }

    estadoVazio.classList.add(
        "hidden"
    );

    notificacoes.forEach(
        function (notificacao) {

            tabela.appendChild(
                criarLinha(notificacao)
            );
        }
    );
}


async function carregarNotificacoes() {

    esconderMensagem();

    contador.textContent =
        "Carregando...";

    try {

        const filtros = {

            agravo:
                filtroAgravo.value.trim(),

            nomePaciente:
                filtroNome.value.trim(),

            duplicadas:
                filtroDuplicadas.checked
        };

        const notificacoes =
            await listarNotificacoes(
                filtros
            );

        renderizarTabela(
            notificacoes
        );

    } catch (erro) {

        contador.textContent =
            "Erro ao carregar";

        exibirMensagem(
            erro.message,
            "error"
        );
    }
}


async function excluirRegistro(notificacao) {

    const confirmar =
        window.confirm(
            "Deseja excluir a notificação de "
            + notificacao.nomePaciente
            + "?"
        );

    if (!confirmar) {
        return;
    }

    try {

        await excluirNotificacao(
            notificacao.id
        );

        exibirMensagem(
            "Notificação excluída com sucesso.",
            "success"
        );

        await carregarNotificacoes();

    } catch (erro) {

        exibirMensagem(
            erro.message,
            "error"
        );
    }
}


formFiltros.addEventListener(
    "submit",
    function (event) {

        event.preventDefault();

        carregarNotificacoes();
    }
);


btnLimpar.addEventListener(
    "click",
    function () {

        filtroAgravo.value = "";

        filtroNome.value = "";

        filtroDuplicadas.checked = false;

        carregarNotificacoes();
    }
);


carregarNotificacoes();