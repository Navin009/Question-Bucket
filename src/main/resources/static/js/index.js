const questionListBlock = document.getElementsByClassName("question-list-block");

for (let i = 0; i < questionListBlock.length; i++) {
	questionListBlock[i].addEventListener("click", function () {
		redirectToQuestion(questionListBlock[i].children[0].id);
	});
}

function redirectToQuestion(id) {
	window.location.href = `/question/` + id;
}
