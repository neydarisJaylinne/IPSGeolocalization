function getMinDistance() {
    MinMaxDistanceStatistics('http://localhost:8080/statistics/min');
}

function getMaxDistance() {
    MinMaxDistanceStatistics('http://localhost:8080/statistics/max');
}

function getAverageDistance() {
    AverageDistanceStatistics('http://localhost:8080/statistics/average');
}

function callData() {
    let ip = document.getElementById("ipInput").value;
    const resultDiv = document.getElementById("ipResult");
    resultDiv.innerHTML = '';
    if (isValidIP(ip)) {
        getData('http://127.0.0.1:8080/ips/', ip);
    } else {
        console.log("Formato de IP no válido");
        let error = 'Por favor, ingresa una IP válida en formato IPv4 (ejemplo: 192.168.1.1)';
        resultDiv.innerHTML = `<span style="color:red;">${error}</span>`;
    }
}

function isValidIP(ip) {
    const ipPattern = /^(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}$/;
    return ipPattern.test(ip);
}

function getData(endpoint, ip) {
    const resultDiv = document.getElementById("ipResult");
    resultDiv.innerHTML = '';

    fetch(endpoint + ip)
        .then(response => {
            console.log(ip);
            if (!response.ok) {
                throw new Error('Error fetching data: ' + response.status);
            }

            return response.json()
        })
        .then(data => {
           // console.log(data);
            try {
                const distance = data.distanceToBuenosAires; // Now a direct value
                const userLatitude = data.latitude; // User's latitude
                const userLongitude = data.longitude; // User's longitude
                const buenosAiresLatitude = data.buenosAiresLatitude; // Buenos Aires latitude
                const buenosAiresLongitude = data.buenosAiresLongitude; // Buenos Aires longitude
                const languages = data.languages.map(lang => {
                    return `${lang.name} (${lang.code})`;
                }).join(', ');
                const formattedTimeZones = data.timeZones.map(timezone => {
                    const date = new Date(timezone);
                    const utcTime = date.toLocaleTimeString('es-ES', {
                        timeZone: 'UTC',
                        hour: '2-digit',
                        minute: '2-digit',
                        second: '2-digit'
                    });
                    const localTime = date.toLocaleTimeString('es-ES', {
                        hour: '2-digit',
                        minute: '2-digit',
                        second: '2-digit'
                    });
                    const offsetHours = -(date.getTimezoneOffset() / 60);
                    const offset = offsetHours === 0 ? "UTC" : `UTC${offsetHours > 0 ? `+${offsetHours}` : offsetHours}`;
                    return `${utcTime} (UTC) o ${localTime} (${offset})`;
                }).join(", ");

                const resultHtml = `
        <strong>IP:</strong> ${data.ip}<br>
        <strong>Fecha actual:</strong> ${new Date(data.localDateTime).toLocaleString('es-ES')}<br>
        <strong>País:</strong> ${data.name} (${data.name.toLowerCase()})<br>
        <strong>ISO Code:</strong> ${data.isoCode}<br>
        <strong>Idiomas:</strong> ${languages}<br>
        <strong>Moneda:</strong> ${data.currencyCode} (1 ${data.currencyCode} = ${data.currencyExchangeRateWithUsDollar.toFixed(4)} U$S)<br>
        <strong>Hora:</strong> ${formattedTimeZones}<br>
        <strong>Distancia estimada:</strong> ${distance.toFixed(2)} kms (${userLatitude.toFixed(2)}, ${userLongitude.toFixed(2)}) a (${buenosAiresLatitude.toFixed(2)}, ${buenosAiresLongitude.toFixed(2)})<br>
    `;

                resultDiv.innerHTML = resultHtml;
            } catch (error) {
                resultDiv.innerHTML = `<span style="color:red;">${error.message}</span>`;
            }
        })
        .catch(error => console.error('Error:', error));
}

async function MinMaxDistanceStatistics(endpoint) {
    let distanceResultDiv = document.getElementById('distanceResult');
    distanceResultDiv.innerHTML = ''; // Clear previous distance results

    try {

        fetch(endpoint)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error fetching data: ' + response.status);
                }
                return response.json()
            })
            .then(data => {
                console.log(data);
                for(let i = 0; i < data.length; i ++ ) {
                    console.log(data[i]);
                    var values = data[i];
                    const distanceNum = parseFloat(values.distance);
                    const resultHtml = `Distancia a Buenos Aires: ${distanceNum.toFixed(2)} kms`;
                    distanceResultDiv.innerHTML = resultHtml;
                }
            })
            .catch(error => console.error('Error:', error));
    } catch
        (error) {
        distanceResultDiv.innerHTML = `<span style="color:red;">${error.message}</span>`; // Show error message
    }
}

async function AverageDistanceStatistics(endpoint) {
    let distanceResultDiv = document.getElementById('distanceResult');
    distanceResultDiv.innerHTML = ''; // Clear previous distance results

    try {
        fetch(endpoint)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error fetching data: ' + response.status);
                }
                return response.json()
            })
            .then(data => {
                console.log(data);
                for(let i = 0; i < data.length; i ++ ) {
                    var value = data[i];
                    const distanceNum = parseFloat(value);
                    const resultHtml = `Valor promedio: ${distanceNum.toFixed(2)} kms`;
                    distanceResultDiv.innerHTML = resultHtml;
                }
            })
            .catch(error => console.error('Error:', error));
    } catch
        (error) {
        distanceResultDiv.innerHTML = `<span style="color:red;">${error.message}</span>`; // Show error message
    }
}