let student_form = document.getElementById('student-validation');
let course_form = document.getElementById('course-validation');
window.onload = fetch_courses;

student_form.addEventListener('submit', async (e) => {
  e.preventDefault();
  e.stopPropagation();
  if (student_form.checkValidity() === true) {
    let response = await fetch('api/students/register', {
      method: 'POST',
      headers: {
          'Content-Type': 'application/json;charset=utf-8'
      },
      body: JSON.stringify({
          first_name: document.getElementById('first_name').value,
          last_name: document.getElementById('last_name').value,
          email: document.getElementById('email').value,
      })
    });
    let result = await response;
    console.log(result);
  }
  student_form.classList.add('was-validated');
});

course_form.onsubmit = async (e) => {
    e.preventDefault();
    e.stopPropagation();
    if (course_form.checkValidity() === true) {
      let form_data = new FormData();
      form_data.append('name', document.getElementById('name').value);
      form_data.append('description', document.getElementById('description').value);
      form_data.append('credits', document.getElementById('credits').value);
        // $.ajax({
        //   type: "POST",
        //   url: "api/courses/register",
        //   enctype: 'multipart/form-data',
        //   data: form_data,
        //   processData: false,
        //   contentType: false,
        // }).done(function(response, status) {
        //   console.log(response, status);
        // });
        let response = await fetch('api/courses/register', {
        method: 'POST',
        body: form_data
      });
      let result = await response;
      console.log(result);
    }
    course_form.classList.add('was-validated');
};

async function fetch_courses(){
    let response = await fetch("api/courses/get");
    let courses = await response.json(); // read response body and parse as JSON
    console.log(courses);
    let courses_option = document.getElementById('courses');
    courses_option.innerHTML = '<option value=""> Choose...</option>';

    for(let i = 0 ; i<courses.length ; i++){
        courses_option.innerHTML += '<option value="'+courses[i]+'">'+courses[i]+'</option>';
    }
}