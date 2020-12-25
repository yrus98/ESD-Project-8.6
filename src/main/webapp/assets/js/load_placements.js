
let cv_file = document.getElementById("cv_application");
let hist_btn = document.getElementById("hist-tab");
document.getElementById('cv_application').onchange = function(){
    document.getElementById('upload_file_name').innerText = document.getElementById('cv_application').files[0].name;
};

async function fetchPlacements()
{
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

window.onload = fetchPlacements().then( result => {
    console.log("this is result->");

    console.log(result.student_data);
    document.getElementById("ppfullname").innerHTML =result.student_data.first_name + " " + result.student_data.last_name;
    document.getElementById("pprollnumber").innerHTML =result.student_data.roll_no;
    document.getElementById("ppemail").innerHTML =result.student_data.email;
    document.getElementById("ppdomain").innerHTML =result.student_data.domain;
    document.getElementById("ppspecial").innerHTML =(result.student_data.spec==="")? 'None' : result.student_data.spec;
    document.getElementById("ppcgpa").innerHTML =result.student_data.cgpa.toFixed(2);
    document.getElementById("img1").src=result.student_data.photo_path;

    document.getElementById("student_id").innerText = result.student_data.student_id;


    //If already placed
    if(result.student_data.isPlaced === 'true'){
        let div = document.createElement('div');
        div.className = 'card text-white bg-success mb-3';

        let div2 = document.createElement('div');
        div2.className = 'card-header';
        div2.innerHTML = 'Congratulations! , you have been placed at ';
        div.appendChild(div2);

        let div3 = document.createElement('div');
        div3.className = 'card-body';

        let temp = document.createElement('h5');
        temp.className ='card-title';
        temp.innerHTML = result.company_details.org_name;
        div3.appendChild(temp);

        temp = document.createElement('h5');
        temp.className ='card-title';
        temp.innerHTML = 'Profile : ' + result.company_details.profile;
        div3.appendChild(temp);

        temp = document.createElement('p');
        temp.className ='card-text';
        temp.innerHTML = 'Job Description : ' + result.company_details.description;
        div3.appendChild(temp);
        div.appendChild(div3);
        document.getElementById('showData').appendChild(div);
        return;
    }

//                     <div class="card border border-primary mb-3" >
//                       <div class="card-header">Header</div>
//                       <div class="card-body text-dark">
//                         <h5 class="card-title">Dark card title</h5>
//                         <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
//                         <a href="#" class="btn btn-primary">Go somewhere</a>
//                       </div>
    //Load data into table
    let pdata = result.placement_list;
    let btns = [];
    // CREATE DYNAMIC TABLE.
    for(let i = 0; i < pdata.length; i++){
        let div = document.createElement('div');
        div.className = 'card border border-primary mb-3';

        let div2 = document.createElement('div');
        div2.className = 'card-header';
        div2.innerHTML = pdata[i]['org_name'];
        div.appendChild(div2);

        let div3 = document.createElement('div');
        div3.className = 'card-body text-dark';

        let temp = document.createElement('h5');
        temp.className ='card-title';
        temp.innerHTML = pdata[i]['profile'];
        div3.appendChild(temp);

        temp = document.createElement('p');
        temp.className ='card-text';
        temp.innerHTML = pdata[i]['description'];
        div3.appendChild(temp);

        temp = document.createElement('p');
        temp.className ='card-text';
        temp.innerHTML = 'Intake : ' + pdata[i]['intake']+ ' &emsp; Minimum Grade : ' + pdata[i]['minimum_grade'];
        div3.appendChild(temp);

        let btn = document.createElement('button');
        btn.setAttribute("class","btn btn-primary");
        btn.name=pdata[i]["placement_id"];
        btn.value="Apply";
        btn.innerText="Apply";
        btns.push(btn);
        div3.appendChild(btn);

        div.appendChild(div3);
        document.getElementById('showData').appendChild(div);

    }

    for(let i=0; i<btns.length;i++){
        (function(i) {
            btns[i].onclick = async function() {
                // window.sessionStorage["apply_plac_id"] = btns[i].name;
                btns[i].innerText = "Applied";
                btns[i].className = 'btn btn-success';
                btns[i].disabled = true;
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
        let col2names= ['Organization Name', 'Profile', 'About','Acceptance Status','Comments','Application Date'];
        let col2 = ['org_name','profile','about','acceptance','comments','date'];

        // CREATE DYNAMIC TABLE.
        var table2 = document.createElement("table");
        //-- adding class --"//
        table2.setAttribute("class","table table-striped table-hover")
        // adding bootstrap class to the table

        // CREATE HTML TABLE HEADER ROW USING THE EXTRACTED HEADERS ABOVE.

        var tr = table2.insertRow(-1);                   // TABLE ROW.

        for (i = 0; i < col2.length; i++) {
            let th = document.createElement("th");      // TABLE HEADER.
            th.innerHTML = col2names[i];
            tr.appendChild(th);
        }

        // ADD JSON DATA TO THE TABLE AS ROWS.
        for (i = 0; i < pdata2.length; i++) {

            tr = table2.insertRow(-1);
            if(pdata2[i]['acceptance']==='ACCEPTED'){
                tr.className="table-success";
            }else if(pdata2[i]['acceptance']==='REJECTED'){
                tr.className="table-danger";
            }

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

