if( window.Annotation == undefined){
    window.Annotation = function (target_id, this_id){
        this.target_component = $(target_id);
        this.this_component = $(this_id);

        var target = '#<wicket:container wicket:id="referenced_id" />';
        var thiz = '#<wicket:container wicket:id="this_id" />';

    }

    window.Annotation.prototype = {
        setup : function(){
            var thiz = this;
            this.target_component.append(
                $('<div>')
                    .attr('style','position:absolute;top:0px;right: 15px;')
                    .append('comentar')
                    .click(function(){
                        console.log(thiz.this_component, thiz.this_component.is(":visible"));
                        if(!thiz.this_component.is(":visible")){
                            thiz.this_component.fadeIn();
                        }else{
                            thiz.this_component.fadeOut();
                        }
                    })
            );
            thiz.this_component.css('position','absolute')
            var target_offset = thiz.target_component.parent().offset()['top'],
                this_offset = thiz.this_component.parent().offset()['top'];
            console.log(thiz, target_offset, this_offset);
            thiz.this_component.css('top',(target_offset-this_offset)+"px");
            //this.this_component.hide();

        }
    }


}
