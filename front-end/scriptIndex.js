// Setto URL
const apiUrl = 'http://localhost:8080/api/v1/pizzas';

// Seleziono root dove iniettare template
const root = document.getElementById("root");

// Seleziono tasto cerca e inpur di ricerca
const submit = document.getElementById('tasto_cerca');
const input = document.getElementById('search');

// Dichiaro variabili da riempire con template literal successivamente
let contentTable;
let contentSinglePizza;

// Ritorno lista di pizze per metterle in pagina
const getPizzas = async (search) => {
    // Se parametro di ricerca non è stato passato lo setto come stringa vuota
    if (search == undefined) {
        search = "";    // Risultato: ?search=""
    }
    try {
        let response = await axios.get(apiUrl + '?search=' + search);
        renderPizzaList(response.data);
    } catch (e) {
        console.log(e);
    }
};

// Ciclo su ogni pizza e chiamo metodo per creare template literal
const renderPizzaList = (data) => {
    contentTable = "";
    if (data.length > 0) {
        data.forEach((element) => {
            contentSinglePizza = renderPizza(element);
            contentTable += contentSinglePizza;
        });
    } else {
        contentTable = '<div class="alert alert-info" style="padding: 30px;">Nessuna pizza presente in lista.</div>';
    }
    root.innerHTML = contentTable;
};

// Creo template literal per ogni pizza e lo inietto in pagina
function renderPizza(element) {
    let infoPizza = `
    <tr style="vertical-align: middle;">
        <td style="font-size: 14px;">
            <div>
                <form
                    id="pizza-delete"
                    onsubmit="return confirm('Sei sicuro?')">
                    <input type="hidden" name="delete" value="${element.id}">
                    <button id="pulsante_elimina" class="pulsante_pizza" type="submit" onclick="deletePizza(${element.id})">
                        <i class="fa-solid fa-trash"></i>
                        Elimina
                    </button>
                </form>
            </div>
        </td>
        <td>€${element.price.toFixed(2)}</td>
        <td>${element.name}</td>
        <td class="descrizione_pizza">${element.description}</td>
        <td>
            <img class="foto_pizza" src="${element.photo}" alt="pizza">
        </td>
    </tr>`;
    return infoPizza;
};

// Al click del pulsante cerca richiedo lista pizze ma passando anche parametro di ricerca
submit.addEventListener('click', (event) => {
    event.preventDefault();
    getPizzas(input.value);
});

// Ritorno lista pizza
getPizzas();

// Metodo per eliminare pizza da ID
function deletePizza(id) {
    deletePizzaById(id);
};

const deletePizzaById = async (id) => {
    try {
        await axios.delete(apiUrl + '/' + id);
        window.location.href = '/front-end/index.html';
    } catch (e) {
        console.log(e);
    }
};