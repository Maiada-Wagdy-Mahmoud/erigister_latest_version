$(document).ready(function () {
    $('.table-striped  .delBtn').on('click',function(event){
        event.preventDefault();
        var href=$(this).attr('href');
        $('#confirmDeleteBookModal #delRef').attr('href',href);
        $('#confirmDeleteBookModal').model();
    })
})