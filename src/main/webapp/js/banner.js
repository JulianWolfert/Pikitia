//created by Peter Horn

//configuration
var intervall = 3000;

// do not change values!
var autoSwitcherOn = true;
var animationIsRunning = false;
var autoSwitcher = window.setInterval("nextImg()", intervall);

window.onload = init;

function init() {
	var $first = $('.banner_imglist img').first();
	var banner_height = $('.banner_imglist').height();
	var banner_width = $('.banner_imglist').width();

	$('.banner_imglist img').each(function() {
		img_height = $(this).height();
		img_width = $(this).width();

		scale = banner_width / img_width;
		$(this).height(img_height * scale);
		$(this).width(banner_width);

		if($(this).height() >= banner_height){
			img_magin_top = "-" + ($(this).height() - banner_height) / 2 + "px";
			$(this).css("margin-top", img_magin_top);
		}else if($(this).height() < banner_height){
			img_magin_top = (banner_height - $(this).height()) / 2 + "px";
			$(this).css("margin-top", img_magin_top);
		}
	});

	$first.addClass('active');
	$first.show();
}

function resetAutoSwitcher() {
	window.clearInterval(autoSwitcher);
	autoSwitcher = window.setInterval("nextImg()", intervall);
	autoSwitcherOn = true;
	$(".banner_play_pause").attr("src", "images/banner/pause.png");
}

function stopAutoSwitcher() {
	if (autoSwitcherOn) {
		window.clearInterval(autoSwitcher);
		autoSwitcherOn = false;
		$(".banner_play_pause").attr("src", "images/banner/play.png");
	}
}

function nextImg() {
	if (animationIsRunning) {
		return;
	}

	var $active = $('.banner_imglist img.active');
	var $next = $active.next();

	if ($next.length == 0) {
		$next = $('.banner_imglist img').first();
	}
	$next.addClass('active');
	$active.removeClass('active');

	animationIsRunning = true;
	$next.stop().show("slide", {
		direction : "left"
	}, 1000);
	$active.stop().hide("slide", {
		direction : "right"
	}, 1000, function() {
		animationIsRunning = false;
	});
}

function prevImg() {
	if (animationIsRunning) {
		return;
	}

	var $active = $('.banner_imglist img.active');
	var $prev = $active.prev();

	if ($prev.length == 0) {
		$prev = $('.banner_imglist img').last();
	}
	$prev.addClass('active');
	$active.removeClass('active');

	animationIsRunning = true;
	$prev.show("slide", {
		direction : "right"
	}, 1000);
	$active.stop().hide("slide", {
		direction : "left"
	}, 1000, function() {
		animationIsRunning = false;
	});
}

function clickNext() {
	stopAutoSwitcher();
	nextImg();
}

function clickPrev() {
	stopAutoSwitcher();
	prevImg();
}

function clickPlayPause() {
	if (!autoSwitcherOn) {
		nextImg();
		resetAutoSwitcher();
	} else {
		stopAutoSwitcher();
	}
}