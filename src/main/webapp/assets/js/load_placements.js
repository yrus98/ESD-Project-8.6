
let cv_file = document.getElementById("cv_application");
let hist_btn = document.getElementById("histBtn");
async function fetchPlacements(){
    let i;
    let response = await fetch('api/placements/show_list', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({
            email: window.sessionStorage["curr_email"],
        })
    });
    const result = await response.json();
    return result;
}

window.onload = fetchPlacements().then(result => {

    // var pdata = JSON.parse(JSON.stringify(result)).placement_list;
    //Load data into table
    document.getElementById("student_id").innerText = result.student_data.student_id;
    let pdata = result.placement_list;
    var col = [];
    for (i = 0; i < pdata.length; i++) {
        for (var key in pdata[i]) {
            if (col.indexOf(key) === -1) {
                col.push(key);
            }
        }
    }

    // CREATE DYNAMIC TABLE.
    var table = document.createElement("table");

    // CREATE HTML TABLE HEADER ROW USING THE EXTRACTED HEADERS ABOVE.

    var tr = table.insertRow(-1);                   // TABLE ROW.

    for (i = 0; i < col.length; i++) {
        let th = document.createElement("th");      // TABLE HEADER.
        th.innerHTML = col[i];
        tr.appendChild(th);
    }
    let th = document.createElement("th");
    th.innerHTML = "";
    tr.appendChild(th);

    // ADD JSON DATA TO THE TABLE AS ROWS.
    btns =[];
    for (i = 0; i < pdata.length; i++) {

        tr = table.insertRow(-1);

        for (var j = 0; j < col.length; j++) {
            var tabCell = tr.insertCell(-1);
            tabCell.innerHTML = pdata[i][col[j]];
        }
        var applyBtn = tr.insertCell(-1);
        var btn = document.createElement('button');
        btn.name=pdata[i]["placement_id"];
        btn.value="Apply";
        btn.innerText="Apply";
        btns.push(btn);
        // btn.onclick = function(){
        //     window.sessionStorage["apply_plac_id"] = btn.name;
        //     window.location.href = "applyForm.html";
        // };
        applyBtn.appendChild(btn);
    }
    for(let i=0; i<btns.length;i++){
        (function(i) {
            btns[i].onclick = async function() {
                // window.sessionStorage["apply_plac_id"] = btns[i].name;
                var cv_application="";
                if(cv_file.files!==undefined && cv_file.files.length > 0){
                    var reader = new FileReader();
                    reader.onload = async function () {
                        // console.log(reader.result);
                        cv_application = reader.result;
                        let form_data = new FormData();
                        form_data.append('placement_id', btns[i].name);
                        form_data.append('student_id', document.getElementById("student_id").innerText);
                        form_data.append('about', "");
                        form_data.append('cv_application', cv_application);
                        form_data.append('acceptance', "PENDING");
                        form_data.append('comments', "");
                        form_data.append('date', new Date().getFullYear() + "-"+(new Date().getMonth()+1)+"-"+new Date().getDate());
                        let response = await fetch('api/students/apply', {
                            method: 'POST',
                            body: form_data
                        });
                        const result = await response.json();
                        return result;
                    }
                    reader.readAsBinaryString(cv_file.files[0]);
                }else{
                    let form_data = new FormData();
                    form_data.append('placement_id', btns[i].name);
                    form_data.append('student_id', document.getElementById("student_id").innerText);
                    form_data.append('about', "");
                    form_data.append('cv_application', cv_application);
                    form_data.append('acceptance', "PENDING");
                    form_data.append('comments', "");
                    form_data.append('date', new Date().getFullYear() + "-"+(new Date().getMonth()+1)+"-"+new Date().getDate());
                    let response = await fetch('api/students/apply', {
                        method: 'POST',
                        body: form_data
                    });
                    const result = await response.json();
                    return result;

                }

            }
        })(i);
    }

    // FINALLY ADD THE NEWLY CREATED TABLE WITH JSON DATA TO A CONTAINER.
    var divContainer = document.getElementById("showData");
    divContainer.innerHTML = "";
    divContainer.appendChild(table);

});

async function see_hist(){
    console.log('watup');
    let i;
    let response = await fetch('api/students/show_hist', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({
            email: window.sessionStorage["curr_email"],
        })
    });
    const res2 = await response.json();
    return res2;
}

hist_btn.addEventListener('click', function(){
    see_hist().then(res2 => {

        // var pdata = JSON.parse(JSON.stringify(result)).placement_list;
        //Load data into table
        let i;
        let pdata2 = res2.application_list;
        console.log(res2);
        var col2 = [];
        for (i = 0; i < pdata2.length; i++) {
            for (let key in pdata2[i]) {
                if (col2.indexOf(key) === -1) {
                    col2.push(key);
                }
            }
        }

        // CREATE DYNAMIC TABLE.
        var table2 = document.createElement("table");

        // CREATE HTML TABLE HEADER ROW USING THE EXTRACTED HEADERS ABOVE.

        var tr = table2.insertRow(-1);                   // TABLE ROW.

        for (i = 0; i < col2.length; i++) {
            let th = document.createElement("th");      // TABLE HEADER.
            th.innerHTML = col2[i];
            tr.appendChild(th);
        }

        // ADD JSON DATA TO THE TABLE AS ROWS.
        for (i = 0; i < pdata2.length; i++) {

            tr = table2.insertRow(-1);

            for (let j = 0; j < col2.length; j++) {
                let tabCell = tr.insertCell(-1);
                tabCell.innerHTML = pdata2[i][col2[j]];
            }
        }

        // FINALLY ADD THE NEWLY CREATED TABLE WITH JSON DATA TO A CONTAINER.
        var divContainer2 = document.getElementById("showHist");
        divContainer2.innerHTML = "";
        divContainer2.appendChild(table2);

    })
});
