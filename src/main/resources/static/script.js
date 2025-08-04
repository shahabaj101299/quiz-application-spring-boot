let questions = [];

window.onload = function () {
  fetch('http://localhost:8080/quiz/questions')
    .then(response => response.json())
    .then(data => {
      questions = data;
      showQuestions(data);
    })
    .catch(err => {
      document.getElementById("quiz-container").innerHTML = "Failed to load questions";
      console.error(err);
    });
};

function showQuestions(questions) {
  const container = document.getElementById("quiz-container");
  container.innerHTML = "";

  questions.forEach((q, i) => {
    container.innerHTML += `
      <div>
        <p><b>${i + 1}. ${q.question}</b></p>
        ${q.options.map(opt => `
          <label>
            <input type="radio" name="q${i}" value="${opt}"> ${opt}
          </label><br/>
        `).join('')}
      </div><hr/>
    `;
  });
}

function submitQuiz() {
  const userAnswers = [];

  questions.forEach((q, i) => {
    const selected = document.querySelector(`input[name="q${i}"]:checked`);
    userAnswers.push({
      questionId: q.id,         // assumes you have ID
      answer: selected ? selected.value : ""
    });
  });

  fetch('http://localhost:8080/quiz/submit', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(userAnswers)
  })
  .then(res => res.text()) // or .json() based on your API
  .then(score => {
    document.getElementById("result").innerHTML = `<h3>Your Score: ${score}</h3>`;
  });
}
