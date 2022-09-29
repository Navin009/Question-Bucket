const navUserDropdown = document.getElementById("user-nav-dropdown");
const userContainer = document.getElementById("und-container");

const userBtn = document.getElementById("user-btn");

userBtn.innerHTML = userBtn.innerHTML.charAt(0);

function showUserOptions() {
	navUserDropdown.style.display = "block";
}

window.addEventListener("mouseup", (event) => {
	if (!userContainer.contains(event.target)) {
		navUserDropdown.style.display = "none";
	}
});
