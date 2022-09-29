const navToggle = document.getElementById("header-nav-toggle");
const nav = document.getElementById("header-nav");

navToggle.addEventListener("click", () => {
	if (navToggle.getAttribute("aria-expanded") === "false") {
		nav.setAttribute("aria-hidden", "false");
		navToggle.setAttribute("aria-expanded", "true");
	} else {
		nav.setAttribute("aria-hidden", "true");
		navToggle.setAttribute("aria-expanded", "false");
	}
});

const pass = document.getElementById("password");
const confirmPass = document.getElementById("confirm-password");
const submitBtn = document.getElementById("submit-btn");
function validatePassword() {
	if (pass.value != confirmPass.value) {
		alert("Passwords do not match");
		submitBtn.disabled = true;
	} else {
		submitBtn.disabled = false;
	}
}
