// Setto URL
const apiUrl = 'http://localhost:8080/api/v1/pizzas';

// Seleziono FORM
const form = document.getElementById('pizza-create');

// Seleziono root dove iniettare template
const root = document.getElementById("root");

// Ritorno lista di ingredienti per metterli in pagina create
const getIngredients = async () => {
    try {
        let response = await axios.get(apiUrl + '/ingredients');
        renderIngredientsList(response.data);
    } catch (e) {
        console.log(e);
    }
};

// Ciclo su ogni ingrediente e chiamo metodo per creare template literal
const renderIngredientsList = (data) => {
    contentTable = "";
    if (data.length > 0) {
        data.forEach((element) => {
            contentSingleIngredient = renderIngredient(element);
            contentTable += contentSingleIngredient;
        });
    } else {
        contentTable = '<div class="alert alert-info" style="padding: 30px;">La lista degli ingredienti è vuota</div>';
    }
    root.innerHTML = contentTable;
};

// Creo template literal per ogni ingrediente e lo inietto in pagina
function renderIngredient(element) {
    let infoIngredient = `
    <div class="form-check" style="position: relative; right: 10px;">
        <input
                class="form-check-input"
                type="checkbox"
                value="${element.id}"
                id="${element.id}"
                name="ingredients[]">
        <label class="form-check-label" for="${element.id}">${element.name}</label>
    </div>
    `;
    return infoIngredient;
};

// Ritorno lista ingredienti
getIngredients();

// Funzione che parte al click sul pulsante SALVA
form.addEventListener('submit', async function (event) {
    // Disattivo comportamento default del pulsante
    event.preventDefault();

    // Prendo i dati dal form e li salvo in delle variabili
    const formData = new FormData(form);
    const name = formData.get('name');
    const price = formData.get('price');
    const photo = formData.get('photo');
    const description = formData.get('description');
    const checkboxes = document.querySelectorAll('input[type="checkbox"]');

    // Creo array di ingredienti e lo popolo
    const ingredients = [];
    for (const checkbox of checkboxes) {
        if (checkbox.checked) {
            const ingredient = {
                id: parseInt(checkbox.value),
                name: checkbox.label,
            };
            ingredients.push(ingredient);
        }
    }

    // Trasformo tutto in JSON
    const jsonData = {
        name: name,
        price: price,
        photo: photo,
        description: description,
        ingredients: ingredients,
    };

    const config = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    // Invio al RestController su Java per creare nuova pizza
    try {
        await axios.post(apiUrl, JSON.stringify(jsonData), config);
        window.location.href = '/front-end/index.html';
    } catch (error) {
        console.error(error);
    }
});