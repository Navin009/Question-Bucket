const answers = document.getElementsByClassName("answer");

function showAnswer(element) {
	Array.from(answers).forEach((element) => {
		element.setAttribute("aria-hidden", "true");
	});
	const parentBlock = element.parentElement.parentElement;
	const answerBlock = parentBlock.getElementsByClassName("answer")[0];
	const ariaHidden = answerBlock.getAttribute("aria-hidden");
	answerBlock.setAttribute("aria-hidden", ariaHidden === "true" ? "false" : "true");
}
