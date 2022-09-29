const answerInput = document.getElementById("answer-input");
const saveBtnBlock = document.getElementById("qab-block");

const question = document.getElementById("question-input");

const grade = document.getElementById("question-grade");
const subject = document.getElementById("question-subject");

function saveQuestion() {
	let questionTitle = createTitleString(question.value);
	let xhr = new XMLHttpRequest();
	xhr.open("POST", "/api/v1/question/create");
	xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	let requestBody = {
		question: question.value,
		questionTitle: questionTitle,
		answer: answerInput.value,
		grade: { id: grade.value },
		subject: { id: subject.value },
	};
	xhr.send(JSON.stringify(requestBody));
	xhr.onload = function () {
		if (xhr.status != 200) {
			alert(`Error ${xhr.status}: ${xhr.statusText}`);
		} else {
			alert(`Done, Question created!`);
			let response = JSON.parse(xhr.response);
			let questionId = response.questionId;
			location.href = `/question/${questionId}`;
		}
	};

	xhr.onerror = function () {
		alert("Request failed");
	};
}

function createTitleString(questionString) {
	questionString = questionString.replace(/[^a-zA-Z0-9\ ]/gi, "");
	questionString = questionString.split(" ", 10).join("-").toLowerCase();
	return questionString;
}

function answerInputToggle() {
	answerInput.toggleAttribute("hidden");
	if (answerInput.getAttribute("hidden") != null) {
		document.getElementsByClassName("add-answer")[0].innerHTML = "Add Answer";
	} else {
		answerInput.value = "";
		document.getElementsByClassName("add-answer")[0].innerHTML = "Remove Answer";
	}
}

function editQuestion() {
	let questionId = location.pathname.split("/").pop();
	let requestBody = {
		id: questionId,
		question: question.value,
	};
	let xhr = new XMLHttpRequest();
	xhr.open("PATCH", "/api/v1/question/edit");
	xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

	xhr.send(JSON.stringify(requestBody));
	xhr.onload = function () {
		if (xhr.status != 200) {
			alert(`Error ${xhr.status}: ${xhr.statusText}`);
		} else {
			alert(`Done, got ${xhr.response.length} bytes`);
		}
	};

	xhr.onprogress = function (event) {
		if (event.lengthComputable) {
			alert(`Received ${event.loaded} of ${event.total} bytes`);
		} else {
			alert(`Received ${event.loaded} bytes`);
		}
	};

	xhr.onerror = function () {
		alert("Request failed");
	};
}

function editAnswer() {
	let answerId = location.pathname.split("/").pop();

	let requestBody = {
		answerId: answerId,
		answer: answerInput.value,
	};

	let xhr = new XMLHttpRequest();
	xhr.open("PATCH", "/api/v1/answer/edit");
	xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	xhr.send(JSON.stringify(requestBody));
	xhr.onload = function () {
		if (xhr.status != 200) {
			alert(`Error ${xhr.status}: ${xhr.statusText}`);
		} else {
			alert(`Done, got ${xhr.response.length} bytes`);
		}
	};

	xhr.onprogress = function (event) {
		if (event.lengthComputable) {
			alert(`Received ${event.loaded} of ${event.total} bytes`);
		} else {
			alert(`Received ${event.loaded} bytes`);
		}
	};

	xhr.onerror = function () {
		alert("Request failed");
	};
}

function createAnswer() {
	let questionId = location.pathname.split("/").slice(-2, -1)[0];
	alert(questionId);
	let requestBody = {
		questionId: questionId,
		answer: answerInput.value,
	};

	let xhr = new XMLHttpRequest();
	xhr.open("POST", "/api/v1/c=answer/create");
	xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	xhr.send(JSON.stringify(requestBody));
	xhr.onload = function () {
		if (xhr.status != 200) {
			alert(`Error ${xhr.status}: ${xhr.statusText}`);
		} else {
			alert(`Done, got ${xhr.response.length} bytes`);
		}
	};

	xhr.onprogress = function (event) {
		if (event.lengthComputable) {
			alert(`Received ${event.loaded} of ${event.total} bytes`);
		} else {
			alert(`Received ${event.loaded} bytes`);
		}
	};

	xhr.onerror = function () {
		alert("Request failed");
	};
}
