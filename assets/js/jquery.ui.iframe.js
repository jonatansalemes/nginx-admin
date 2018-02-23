(function($) {
	$.widget("bs.iframe", {
		options: {
			interval : 1000,
			offset : 50,
			min : 0
		},
		_create: function() {
			var self = this;
			var iframe = self.element;
			iframe.load(function(){
				self._reset();
				self._resize();
			});
			setInterval(function(){
				self._resize();
			},self.options.interval);
		},
		_reset : function () {
			var self = this;
			var iframe = self.element;
			iframe.css('height',self.options.min + 'px');
		},
		_resize : function (){
			var self = this;
			var iframe = self.element;
			if(iframe && iframe.contents() && iframe.contents().outerHeight()){
				var height = iframe.contents().outerHeight();
				if(iframe.height() < height) {
					iframe.css('height', (height + self.options.offset) +'px');
				}
			}
		}
	});
})(jQuery);




