const form =
    document.getElementById("formNotificacao");

const mensagem =
    document.getElementById("mensagem");

const tituloPagina =
    document.getElementById("tituloPagina");

const btnSalvar =
    document.getElementById("btnSalvar");

const agravo =
    document.getElementById("agravo");

const dataNotificacao =
    document.getElementById("dataNotificacao");

const nomePaciente =
    document.getElementById("nomePaciente");

const dataNascimento =
    document.getElementById("dataNascimento");

const idade =
    document.getElementById("idade");

const nomeMae =
    document.getElementById("nomeMae");

const sexo =
    document.getElementById("sexo");

const gestante =
    document.getElementById("gestante");

const paisResidencia =
    document.getElementById("paisResidencia");

const ufResidencia =
    document.getElementById("ufResidencia");

const municipioResidencia =
    document.getElementById("municipioResidencia");

const parametros =
    new URLSearchParams(window.location.search);

const id =
    parametros.get("id");

function exibirMensagem(texto, tipo) {

    mensagem.textContent = texto;

    mensagem.className =
        alert ${tipo};
}

function esconderMensagem() {

    mensagem.className =
        "alert hidden";

    mensagem.textContent = "";
}

function atualizarCamposCondicionais() {

    idade.required =
        !dataNascimento.value;

    const pacienteFeminino =
        sexo.value === "F";

    gestante.disabled =
        !pacienteFeminino;

    gestante.required =
        pacienteFeminino;

    if (!pacienteFeminino) {
        gestante.value = "";
    }

    const pais =
        paisResidencia.value
            .trim()
            .toLowerCase();

    const residenteBrasil =
        pais === ""
        || pais === "brasil";

    ufResidencia.required =
        residenteBrasil;

    municipioResidencia.required =
        residenteBrasil
        || ufResidencia.value.trim() !== "";
}

function obterDadosFormulario() {

    return {

        agravo:
            agravo.value.trim(),

        dataNotificacao:
            dataNotificacao.value,

        nomePaciente:
            nomePaciente.value.trim(),

        dataNascimento:
            dataNascimento.value || null,

        idade:
            idade.value
                ? Number(idade.value)
                : null,

        nomeMae:
            nomeMae.value.trim(),

        sexo:
            sexo.value,

        gestante:
            gestante.disabled
                ? null
                : gestante.value,

        paisResidencia:
            paisResidencia.value.trim(),

        ufResidencia:
            ufResidencia.value
                .trim()
                .toUpperCase(),

        municipioResidencia:
            municipioResidencia.value.trim()
    };
}

function preencherFormulario(notificacao) {

    agravo.value =
        notificacao.agravo || "";

    dataNotificacao.value =
        notificacao.dataNotificacao || "";

    nomePaciente.value =
        notificacao.nomePaciente || "";

    dataNascimento.value =
        notificacao.dataNascimento || "";

    idade.value =
        notificacao.idade ?? "";

    nomeMae.value =
        notificacao.nomeMae || "";

    sexo.value =
        notificacao.sexo || "";

    paisResidencia.value =
        notificacao.paisResidencia || "";

    ufResidencia.value =
        notificacao.ufResidencia || "";

    municipioResidencia.value =
        notificacao.municipioResidencia || "";

    atualizarCamposCondicionais();

    if (sexo.value === "F") {

        gestante.value =
            notificacao.gestante || "";
    }
}

async function carregarEdicao() {

    if (!id) {

        atualizarCamposCondicionais();

        return;
    }

    tituloPagina.textContent =
        "Editar notificação";

    btnSalvar.textContent =
        "Salvar alterações";

    try {

        const notificacao =
            await buscarNotificacao(id);

        preencherFormulario(
            notificacao
        );

    } catch (erro) {

        exibirMensagem(
            erro.message,
            "error"
        );

        form.classList.add("hidden");
    }
}

form.addEventListener(
    "submit",
    async (event) => {

        event.preventDefault();

        esconderMensagem();

        const notificacao =
            obterDadosFormulario();

        btnSalvar.disabled = true;

        btnSalvar.textContent =
            id
                ? "Salvando..."
                : "Cadastrando...";

        try {

            if (id) {

                await atualizarNotificacao(
                    id,
                    notificacao
                );

                exibirMensagem(
                    "Notificação atualizada com sucesso.",
                    "success"
                );

            } else {

                await criarNotificacao(
                    notificacao
                );

                exibirMensagem(
                    "Notificação cadastrada com sucesso.",
                    "success"
                );
            }

            setTimeout(() => {

                window.location.href =
                    "/consulta.html";

            }, 800);

        } catch (erro) {

            exibirMensagem(
                erro.message,
                "error"
            );

            btnSalvar.disabled = false;

            btnSalvar.textContent =
                id
                    ? "Salvar alterações"
                    : "Salvar notificação";
        }
    }
);

dataNascimento.addEventListener(
    "change",
    atualizarCamposCondicionais
);

sexo.addEventListener(
    "change",
    atualizarCamposCondicionais
);

paisResidencia.addEventListener(
    "input",
    atualizarCamposCondicionais
);

ufResidencia.addEventListener(
    "input",
    () => {

        ufResidencia.value =
            ufResidencia.value.toUpperCase();

        atualizarCamposCondicionais();
    }
);

carregarEdicao();