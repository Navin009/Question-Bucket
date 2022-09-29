const answerBlock = document.getElementById("answers");

function modAnswer(element) {
	if (element.innerHTML == "UPDATE") {
		location.href = "/update-answer/" + element.id.split("-")[1];
	} else {
		location.href = "/edit-answer/" + element.id.split("-")[1];
	}
}

function modQuestion(element) {
	if (element.innerHTML == "UPDATE") {
		location.href = "/update-question/" + location.pathname.split("/").pop();
	} else {
		location.href = "/edit-question/" + location.pathname.split("/").pop();
	}
}

if (answerBlock.getAttribute("aria-rowcount") === "0") {
	answerBlock.innerHTML = `<div>No answers yet. Be the first to <a href='${
		location.pathname + "/create-answer"
	}'>answer!</a></div>`;
}
