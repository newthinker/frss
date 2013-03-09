
function setImg(o, p, s) {
	var width_img;
	var height_img;
	o.style.visibility = "visible";
	width_img=p.offsetWidth;
	height_img=p.offsetHeight;
	var ratW;        //宽的缩小比例
	var ratH;        //高的缩小比例
	var rat;         //实际使用的缩小比例
	if(width_img<=s.width && height_img<=s.height)
	{
		//如果比预定义的宽高小，原图显示。
		o.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").sizingMethod = "image";
		return;
	}else{
		//如果大的化，要把 sizingMethod改成scale 如果属性是image,不管怎么改div的宽高，都不起作用
		o.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").sizingMethod = "scale";
	}
	ratH=s.height/height_img;
	ratW=s.width/width_img;
	if(ratH<ratW)       //选择最小的作为实际的缩小比例
		rat=ratH;
	else
		rat=ratW;
	width_img=width_img * rat;
	height_img=height_img * rat;
	o.style.width=width_img+"px";
	o.style.height=height_img+"px";
}
function ShowLocalImage(node, node2, path, size){
	if (path && path.length > 0) {
		//处理前是原图，先将其隐藏
		node.style.display = "block";
		node.style.visibility = "hidden";
		node.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = path;
		node2.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = path;
		//过一小会获取div的宽高.
		setTimeout(function() {
			setImg(node, node2, size);
		} ,500);
	}
}
function ShowWebImage(node, path, size) {
	node.src = path;
	var width_img = node.offsetWidth;
	var height_img = node.offsetHeight;
	if(width_img<=s.width && height_img<=s.height)
		return;
	var ratH=s.height/height_img;//宽的缩小比例
	var ratW=s.width/width_img;//高的缩小比例
	var rat=1;         //实际使用的缩小比例
	if(ratH<ratW)       //选择最小的作为实际的缩小比例
		rat=ratH;
	else
		rat=ratW;
	width_img=width_img * rat;
	height_img=height_img * rat;
	o.style.width=width_img+"px";
	o.style.height=height_img+"px";
}
