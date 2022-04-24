function ui() {
    $(function () {
        Quoridor.init('#board');
    });
}

var Quoridor = new function () {
    this.boardDimension = 9;
    this.startingFences = 10;
    this.player1 = null;
    this.player2 = null;
    this.moves = null;
    this.currentTurn = null;

    this.init = function (board) {
        var board = $(board);

        for (var i = 0; i < this.boardDimension; i++) {
            if (i != 0) {
                for (var j = 0; j < this.boardDimension; j++) {
                    var horizontalFence = $('<div class="fence horizontal" />')
                    if (j == 0) {
                        horizontalFence.addClass('left');
                    }
                    if (j == 8) {
                        horizontalFence.addClass('right');
                    }
                    board.append(horizontalFence);
                }
            }

            for (var j = 0; j < this.boardDimension; j++) {
                var squareNumber = (i * this.boardDimension) + j;
                var square = $('<div id="square_' + squareNumber + '" class="square" />')
                if (j != 0) {
                    var fence = $('<div class="fence" />')
                    board.append(fence);
                }
                board.append(square);
            }
        }

        initPlayers();
        bindSquareEventHandlers();
        bindFenceEventHandlers();
        updateInformation();
        board.after(Information.getPanel());
    }

    function bindFenceEventHandlers() {
        $('.fence').each(function (i, v) {
            $(v).attr('id', 'fence_' + i);
        });
        $('.fence').hover(function () {
            $(this).addClass('selected');
            getAdjacentFence(this).addClass('selected');
        }, function () {
            $(this).removeClass('selected');
            getAdjacentFence(this).removeClass('selected');
        });
        $('.fence').click(function () {
            placeFence(this);
        });
    }

    function bindSquareEventHandlers() {
        $('.square').click(function () {
            var newPosition = parseInt($(this).attr('id').split('_')[1]);
            movePlayer(newPosition);
        });
    }

    function initPlayers() {
        this.player1 = new Player("Player 1", 4, "player_1", Quoridor.startingFences);
        this.player2 = new Player("Player 2", 76, "player_2", Quoridor.startingFences);

        currentTurn = this.player1;

        updatePlayerPosition(this.currentTurn.pos);
        switchPlayerFirst();
        updatePlayerPosition(this.currentTurn.pos);
        switchPlayerFirst();
    }

var firstObjectSent;
var secondObjectSent;

    function movePlayer(newPosition) {
        if (isPossibleMove(newPosition)) {
            firstObjectSent = this.currentTurn.pos;
            secondObjectSent = newPosition
            sendMove(firstObjectSent, secondObjectSent)
        }
    }

    movePlayerForReal = function(){
        updatePlayerPosition(secondObjectSent);
        switchPlayer();
        checkWinner();
    }

    function isPossibleMove(newPosition) {
        var isLegal = true;
        return isLegal;
    }

    function updatePlayerPosition(position) {
        this.currentTurn.pos = position;
        var playerDiv = $('<div id="' + this.currentTurn.id + '" />');
        $('#' + this.currentTurn.id).remove();
        $('#square_' + position).append(playerDiv);
    }

    function getAdjacentFence(fence) {
        if ($(fence).hasClass('horizontal')) {
            return $(fence).next();
        }
        else {
            adjacentIndex = parseInt($(fence).attr('id').split('_')[1]) + 17;
            return $($('.fence')[adjacentIndex]);
        }
    }

    function isFencePlaceable(fence) {
        var isLegal = true;


        var nextFence = getAdjacentFence(fence);

        var fenceId = parseInt($(fence).attr('id').split('_')[1]);
        var nextFenceId;

        if ($(fence).hasClass("placed")) {
            alert("There is a fence already there");
            $(isLegal) = false;
        }


        if ($(nextFence).hasClass("placed")) {
            alert("There is a fence already there");
            $(isLegal) = false;
        }


        // find vertical cross
        if (($(fence).hasClass("horizontal"))) {
            nextFenceId = parseInt($(nextFence).attr('id').split('_')[1]);
            topVertical = fenceId - 8;
            bottomVertical = fenceId + 9;

            if ($($('.fence')[topVertical]).hasClass('placed') && $($('.fence')[bottomVertical]).hasClass('placed')) {
                alert("There is a fence already there");
                $(isLegal) = false;
            }

            if ((nextFenceId) != (fenceId + 1)) {
                alert("out of bounds");
                $(isLegal) = false;
            }

        }
        else {
            leftHorizontal = fenceId + 8;
            rightHorizontal = fenceId + 9;;
            if ($($('.fence')[leftHorizontal]).hasClass('placed') && $($('.fence')[rightHorizontal]).hasClass('placed')) {
                alert("There is a fence already there");
                $(isLegal) = false;
            }
            if (fenceId > 135) {
                alert("out of bounds");
                $(isLegal) = false;
            }
        }

        if (!hasFencesRemaining()) {
            alert("No fences left");
            $(isLegal) = false;
        }

        return (isLegal);
    }

    function hasFencesRemaining() {
        return this.currentTurn.fencesRemaining > 0;
    }

    function placeFence(fence) {
        firstObjectSent = fence;
        setUpForSenendFence(fence);
    }

     placeFenceForReal= function()
    {
        if(firstObjectSent!=null)
        {
            var fence = firstObjectSent;
            $(fence).addClass('placed');
            getAdjacentFence(fence).addClass('placed');
            this.currentTurn.fencesRemaining--;
            switchPlayer();
            firstObjectSent = null;
        }

    }


    function setUpForSenendFence(fence) {
        //vertical (a/b - c/d)
        // a | b
        // c | d

        //horizontal (a/b - c/d)
        // a c
        // _ _
        // b d

        var a;
        var b;
        var c;
        var d;
        var fType;

        var fenceId = parseInt($(fence).attr('id').split('_')[1]);
        var nextFence = getAdjacentFence(fence);
        var nextFenceId = parseInt($(nextFence).attr('id').split('_')[1]);

        if (($(fence).hasClass("horizontal"))) {
            //alert("horizontal");
            a = (fenceId - (8 * Math.floor(fenceId / 17)) - 8);
            b = a + 9;
            c = a + 1;
            d = b + 1;
            fType = "h";
        }

        else {
            //alert("vertical");
            a = fenceId - (8 * Math.floor(fenceId / 17));
            b = a + 1;
            c = nextFenceId - (8 * Math.floor(nextFenceId / 17));
            d = c + 1;
            fType = "v";
        }
        //alert("a:" + a + "b:" + b + "c:" + c + "d:" + d);
        //send json
        sendFence(fenceId, nextFenceId, a, b, c, d, fType);

    }

    function updateInformation() {
        Information.currentTurn.text(this.currentTurn.name);
        Information.player1FencesRemaining.text(this.player1.fencesRemaining);
        Information.player2FencesRemaining.text(this.player2.fencesRemaining);
    }

    function checkWinner() {
        if (this.player1.pos > 71) {
            alert("white won");
        }
        else if (this.player2.pos < 9) {
            alert("black won");
        }
    }

    function switchPlayerFirst() {
        if (this.currentTurn == this.player1) {
            this.currentTurn = this.player2;
            
        }
        else {
            this.currentTurn = this.player1;
            
        }
        updateInformation();
    }

    function switchPlayer() {
        if (this.currentTurn == this.player1) {
            this.currentTurn = this.player2;
            //updateInformation();
            // ai turn:
            
            //sendAi();
            
        }
        else {
            this.currentTurn = this.player1;
            
        }
        updateInformation();
    }
};

var Information = new function () {
    this.panel = $('<div id="info" />');
    this.currentTurn = $('<div id="currentTurn" />');
    this.player1FencesRemaining = $('<div id="player1FencesRemaining" />');
    this.player2FencesRemaining = $('<div id="player2FencesRemaining" />');

    this.getPanel = function () {
        this.panel.append(this.currentTurn);
        this.panel.append(this.player1FencesRemaining);
        this.panel.append(this.player2FencesRemaining);
        return this.panel;
    }
}

function Player(name, position, id, fences) {
    this.name = name;
    this.pos = position;
    this.id = id;
    this.fencesRemaining = fences;
}
