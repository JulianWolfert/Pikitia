//created by Peter Horn

var autoSwitcherOn = true;
var intervall = 3000;
var autoSwitcher = window.setInterval("nextImg()", intervall);

window.onload=resetBannerSize;

function resetBannerSize() {
	$('.banner_imglist').width($('.banner_imglist img.active').width());
	$('.banner_imglist').height($('.banner_imglist img.active').height());
}

function resetAutoSwitcher() {
	window.clearInterval(autoSwitcher);
	autoSwitcher = window.setInterval("nextImg()", intervall);
	autoSwitcherOn = true;
}

function stopAutoSwitcher() {
	if (autoSwitcherOn) {
		window.clearInterval(autoSwitcher);
		autoSwitcherOn = false;
	}
}

function nextImg() {
	var $active = $('.banner_imglist img.active');
	var $next = $active.next();

	if ($next.length == 0) {
		$next = $('.banner_imglist img').first();
	}
	$next.addClass('active');
	$active.removeClass('active');
	resetBannerSize();
	
	$next.fadeIn(1000);
	$active.fadeOut(1000);
}

function prevImg() {
	var $active = $('.banner_imglist img.active');
	var $prev = $active.prev();

	if ($prev.length == 0) {
		$prev = $('.banner_imglist img').last();
	}
	$prev.addClass('active');
	$active.removeClass('active');
	resetBannerSize();
	
	$prev.fadeIn(1000);
	$active.fadeOut(1000);
}

function clickNext() {
	stopAutoSwitcher();
	nextImg();
}

function clickPrev() {
	stopAutoSwitcher();
	prevImg();
}

function clickPlay() {
	nextImg();
	resetAutoSwitcher();
}
