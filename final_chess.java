package hash_package;

import java.util.Scanner;

class moves {
    int i_i;
    int i_c;
    int f_i;
    int f_c;
    char colour;

    boolean[][] vm = new boolean[8][8];


    char color(char x) {					//return color codes
        int n = (int) x;
        if (n >= 9812 && n <= 9817)
            return 'w';
        else if (n >= 9818 && n <= 9823)
            return 'b';
        else return 'e';
    }

    boolean[][] vm_ret() {					//return map created
        return vm;
    }

    int fill(char m, int i, int c) {		//fill map
        if (m == ' ') {
            vm[i][c] = true;            //empty
            return 0;
        } else {
            vm[i][c] = true;
            return 1;
        }
    }

    void enemy_area(char turn, char[][] board) {				//enemy_area
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                if (turn != color(board[i][j]) && color(board[i][j]) != 'e') {//opposite players
                    compile(board, i, j, 1);
                }
            }
    }

    void compile(char[][] board, int x, int y, int mk)                    //moves and kill checker
    {
        //mk=1 for kill
        //mk=0 for move&&kill, only for pawn as their moves are not killer each time
        i_i = x;
        i_c = y;
        colour = color(board[i_i][i_c]);
        int i, j;

        switch (board[i_i][i_c]) {
            case '\u2656':    //for rook
            case '\u265C':
                for (i = i_i + 1; i < 8; i++)                                //downward
                    if (fill(board[i][i_c], i, i_c) == 1) break;
                for (i = i_i - 1; i >= 0; i--)                                //upward
                    if (fill(board[i][i_c], i, i_c) == 1) break;
                for (i = i_c + 1; i < 8; i++)                                //to right
                    if (fill(board[i_i][i], i_i, i) == 1) break;
                for (i = i_c - 1; i >= 0; i--)                                //to left
                    if (fill(board[i_i][i], i_i, i) == 1) break;
                break;
            case '\u2657':    //for bishop
            case '\u265D':
                for (i = i_i + 1, j = i_c + 1; (i < 8) && (j < 8); i++, j++)
                    if (fill(board[i][j], i, j) == 1) break;
                for (i = i_i + 1, j = i_c - 1; (i < 8) && (j >= 0); i++, j--)
                    if (fill(board[i][j], i, j) == 1) break;
                for (i = i_i - 1, j = i_c + 1; (i >= 0) && (j < 8); i--, j++)
                    if (fill(board[i][j], i, j) == 1) break;
                for (i = i_i - 1, j = i_c - 1; (i >= 0) && (j >= 0); i--, j--)
                    if (fill(board[i][j], i, j) == 1) break;
                break;
            case '\u2655':    //for queen
            case '\u265B':
                for (i = i_i + 1; i < 8; i++)
                    if (fill(board[i][i_c], i, i_c) == 1) break;
                for (i = i_i - 1; i >= 0; i--)
                    if (fill(board[i][i_c], i, i_c) == 1) break;
                for (i = i_c + 1; i < 8; i++)
                    if (fill(board[i_i][i], i_i, i) == 1) break;
                for (i = i_c - 1; i >= 0; i--)
                    if (fill(board[i_i][i], i_i, i) == 1) break;

                for (i = i_i + 1, j = i_c + 1; (i < 8) && (j < 8); i++, j++)
                    if (fill(board[i][j], i, j) == 1) break;
                for (i = i_i + 1, j = i_c - 1; (i < 8) && (j >= 0); i++, j--)
                    if (fill(board[i][j], i, j) == 1) break;
                for (i = i_i - 1, j = i_c + 1; (i >= 0) && (j < 8); i--, j++)
                    if (fill(board[i][j], i, j) == 1) break;
                for (i = i_i - 1, j = i_c - 1; (i >= 0) && (j >= 0); i--, j--)
                    if (fill(board[i][j], i, j) == 1) break;

                break;
            case '\u2654':  //for  king
            case '\u265A':
                for (i = i_i - 1; i <= i_i + 1 && i < 8; i++) {
                    if (i < 0) i = 0;
                    for (j = i_c - 1; j <= i_c + 1 && j < 8; j++) {
                        if (j < 0) j = 0;
                        if (i == i_i && j == i_c) continue;
                        if (fill(board[i][j], i, j) == 1) ;
                    }
                }

                break;
            case '\u2658':    //for knight
            case '\u265E':
                for (i = i_i - 2; i <= i_i + 2 && i < 8; i = i + 2) {
                    if (i < 0) i = 0;
                    for (j = i_c - 1; j <= i_c + 1 && j < 8; j++) {
                        if (j < 0) j = 0;
                        if (!(Math.abs(i_i - i) == 2 && Math.abs(i_c - j) == 1)) continue;
                        if (fill(board[i][j], i, j) == 1) ;
                    }
                }
                for (i = i_i - 1; i <= i_i + 1 && i < 8; i++) {
                    if (i < 0) i = 0;
                    for (j = i_c - 2; j <= i_c + 2 && j < 8; j = j + 2) {
                        if (j < 0) j = 0;
                        if (!(Math.abs(i_i - i) == 1 && Math.abs(i_c - j) == 2)) continue;
                        if (fill(board[i][j], i, j) == 1) ;
                    }
                }
                break;


            //mk==0 for moves
            //mk==1 for kills
            case '\u265F': //black pawn
                if	(i_i==7) break;
                if (i_i == 1 && board[i_i + 1][i_c] == ' ' && board[i_i + 2][i_c] == ' ' && mk == 0) {//front double move
                    vm[i_i + 2][i_c] = true;
                }
                if (i_i<=6&&board[i_i + 1][i_c] == ' ' && mk == 0) vm[i_i + 1][i_c] = true;    //front move
                if (i_c != 0 && board[i_i + 1][i_c - 1] != ' ' && i_c - 1 > 0)//front left kill
                    vm[i_i + 1][i_c - 1] = true;
                if (i_c != 7 && board[i_i + 1][i_c + 1] != ' ' && i_c + 1 < 8) //front right kill
                    vm[i_i + 1][i_c + 1] = true;

                break;
            case '\u2659': //white pawn
                if(i_i==0) break;
                if (i_i == 6 && board[i_i - 1][i_c] == ' ' && board[i_i - 2][i_c] == ' ' && mk == 0) {//front double move
                    vm[i_i - 2][i_c] = true;
                }
                if (i_i>=1&&board[i_i - 1][i_c] == ' ' && mk == 0) vm[i_i - 1][i_c] = true; //front move
                if (i_c != 0 && board[i_i - 1][i_c - 1] != ' ' && i_c - 1 > 0) //front left kill
                    vm[i_i - 1][i_c - 1] = true;
                if (i_c != 7 && board[i_i - 1][i_c + 1] != ' ' && i_c + 1 < 8)    //front right kill
                    vm[i_i - 1][i_c + 1] = true;


                break;
            default:
                break;
        }
    }

    void vm_show() {								//show created map
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (vm[i][j] == false)
                    System.out.print("****");
                else
                    System.out.print(vm[i][j]);
                System.out.print(' ');
            }
            System.out.println();
        }
    }
}


////////////////////////////////////////////////////////////////////////////////////////////////////
public class Chess_Beta {
    char[][] board = new char[8][8];		//contain board with pieces
    boolean[][] v_map, e_map, c_map, a_map = new boolean[8][8];
    //v_map=valid moves
    //e_map=attack moves by enemy
    //c_map=enemy check
    //a_map=already move
    char turn = 'w';               			//game starts with white turn
    boolean[][] encom = new boolean[2][8];  //en-pasent flag
    char wk = '\u2654';
    char wq = '\u2655';
    char wr = '\u2656';
    char wb = '\u2657';
    char wn = '\u2658';
    char wp = '\u2659';
    char bk = '\u265A';
    char bq = '\u265B';
    char br = '\u265C';
    char bb = '\u265D';
    char bn = '\u265E';
    char bp = '\u265F';

    Chess_Beta() {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                board[i][j] = ' ';

        //initial start
        {	board[0][0] = br;
            board[0][1] = bn;
            board[0][2] = bb;
            board[0][3] = bq;
            board[0][4] = bk;
            board[0][5] = bb;
            board[0][6] = bn;
            board[0][7] = br;
            board[7][0] = wr;
            board[7][1] = wn;
            board[7][2] = wb;
            board[7][3] = wq;
            board[7][4] = wk;
            board[7][5] = wb;
            board[7][6] = wn;
            board[7][7] = wr;

            for (int k = 0; k < 8; k++)
                board[1][k] = bp;
            for (int k = 0; k < 8; k++)
                board[6][k] = wp;
           /*
        	board[0][0]=bk;				//testing area
        	board[7][7]=wk;
        	board[3][3]=wq;
        	board[1][2]=wq;
        	board[4][0]=br;
        	*/
        }
    }

    public static void main(String[] args) {		//main function

        Chess_Beta a = new Chess_Beta();
        a.start();

    }

    void show() {								//print chess board


        for (int i = 1; i <= 8; i++) {
            System.out.print((9 - i) + " ");
            for (int j = 1; j <= 8; j++) {
                if (board[i - 1][j - 1] == ' ')
                    System.out.print('\u2610' + " ");
                else
                    System.out.print(board[i - 1][j - 1] + " ");

            }
            System.out.println();
        }
        System.out.print("  ");
        for (int k = 0; k < 8; k++) {
            System.out.print((char) (65 + k) + " ");
        }
        System.out.println();
    }

    char color(char x) {			//return colour codes
        int n = (int) x;
        if (n >= 9812 && n <= 9817)
            return 'w';
        else if (n >= 9818 && n <= 9823)
            return 'b';
        else return 'e';
    }

    void s_move(int ki, int kc, int ri, int rc, int i_i, int i_c, int f_i, int f_c) {	//for castling moves
        board[i_i][i_c] = ' ';
        board[f_i][f_c] = ' ';
        a_map[i_i][i_c]	= true;
        a_map[f_i][f_c]	= true;
        if (turn == 'w') {
            board[ki][kc] = wk;
            board[ri][rc] = wr;
            turn = 'b';
        } else {
            board[ki][kc] = bk;
            board[ri][rc] = br;
            turn = 'w';
        }

    }

    void se_move(int i_i, int i_c, int f_i, int f_c, int x, int y) {		//for enpassant kill move
        int row;
        if (color(board[x][y]) == 'w')
            row = 0;
        else
            row = 1;
        board[x][y] = ' ';
        move(i_i, i_c, f_i, f_c);
        encom[row][f_c] = false;

    }

    void move(int i_i, int i_c, int f_i, int f_c) {						//for move
        board[f_i][f_c] = board[i_i][i_c];
        board[i_i][i_c] = ' ';
        if(i_i<2||i_i>5) a_map[i_i][i_c] = true;                        //first time moves
        if (turn == 'w')
            turn = 'b';
        else
            turn = 'w';
        if (board[f_i][f_c] == wp && i_i == 6 && f_i == 4) {
            encom[0][i_c] = true;										//white 0

        }
        if (board[f_i][f_c] == bp && i_i == 1 && f_i == 3) {
            encom[1][i_c] = true;										////black 1

        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    int convert(String s) {
        int n = s.length();
        char ch[] = s.toCharArray();
        if (s.equalsIgnoreCase("exit")) return -1;    //exit statement
        if (n != 5) {
            System.out.println("Invalid text");
            return 0;
        }

        int i_i = 56 - ch[1];				//initial integer
        int i_c = ch[0] - 65;				//initial character
        int f_i = 56 - ch[4];				//final integer
        int f_c = ch[3] - 65;				//final character
        char i, f;

        if (i_i < 0 || i_i >= 8
                || i_c < 0 || i_c >= 8
                || f_i < 0 || f_i >= 8
                || f_i < 0 || f_i >= 8
                ) {
            System.out.println("Invalid text");
            return 0;
        }
        i = color(board[i_i][i_c]);
        f = color(board[f_i][f_c]);
        if (i == 'e') {
            System.out.println("Empty Space Selection");
            return 0;
        }
        if (i != turn) {
            System.out.println("Another Player Turn");
            return 0;
        }


        if (board[i_i][i_c] == wk || board[i_i][i_c] == bk) {	//for kings

            moves safe = new moves();
            safe.enemy_area(i, board);
            e_map = safe.vm_ret();                              //e_map denotes enemy area

            /*
             * Castling is possible if
             * rook didnt move previously
             * kings didnt move previously
             * kings is not check
             * kings will not checked
             * king moving passage is not checked
             */
            if (board[i_i][i_c] == wk && a_map[7][4] == false && board[f_i][f_c] == wr && f_i == 7 && f_c == 0)                                //rook at 0,0
            {                                                        //wk left wr castle
                if (a_map[7][0] == false &&
                        e_map[7][2] == false &&
                        e_map[7][3] == false &&
                        e_map[7][4] == false
                        ) {
                    s_move(7, 2, 7, 3, i_i, i_c, f_i, f_c);
                    return 1;
                } else {
                    return 2;
                }
            } else if (board[i_i][i_c] == wk && a_map[7][4] == false && board[f_i][f_c] == wr && f_i == 7 && f_c == 7)                        //rook at 0,7
            {                                                  		  //wk right wr castle
                if (a_map[0][7] == false &&
                        e_map[7][4] == false &&
                        e_map[7][5] == false &&
                        e_map[7][6] == false
                        ) {
                    s_move(7, 6, 7, 5, i_i, i_c, f_i, f_c);
                    return 1;
                } else {
                    return 2;
                }
            } else if (board[i_i][i_c] == bk && a_map[0][4] == false && board[f_i][f_c] == br && f_i == 0 && f_c == 0) {                                                    //bk left br castle
                if (a_map[0][0] == false &&								//bk left br castle
                        e_map[0][2] == false &&
                        e_map[0][3] == false &&
                        e_map[0][4] == false
                        ) {
                    s_move(0, 2, 0, 3, i_i, i_c, f_i, f_c);
                    return 1;
                } else {
                    return 2;
                }
            } else if (board[i_i][i_c] == bk && a_map[0][4] == false && board[f_i][f_c] == br && f_i == 0 && f_c == 7) {                                                    //bk right br castle
                if (a_map[0][7] == false &&								//bk right br castle
                        e_map[0][4] == false &&
                        e_map[0][5] == false &&
                        e_map[0][6] == false
                        ) {
                    s_move(0, 6, 0, 5, i_i, i_c, f_i, f_c);
                    return 1;
                } else {
                    return 2;
                }
            } else if (i == f) {                                     //kings with same pieces
                System.out.println("Invalid move");
                return 0;
            } else {

                moves king = new moves();
                king.compile(board, i_i, i_c, 0);                    //kings valid moves
                v_map = king.vm_ret();									//v_map denotes kings valid move

                for (int a = 0; a < 8; a++) {
                    for (int b = 0; b < 8; b++) {
                        v_map[a][b] = v_map[a][b] && !(e_map[a][b]);    //king valid and safe moves
                        //e_map denotes enemy area
                    }													//now v_map denotes king safe moving area
                }

                if (v_map[f_i][f_c] == true)                            //if final destination is in v_map
                {
                    move(i_i, i_c, f_i, f_c);

                }
                else {
                    System.out.println("Enemy teritorry");
                    return 0;
                }

                /////player turn has been switched
                //turn in following are for other player
                moves this_enemy_area = new moves();
                this_enemy_area.enemy_area(turn, board);
                e_map = this_enemy_area.vm_ret();							//e_map denotes enemy area for this player

                int i_k , j_k=0 , flag = 0;								//for finding this player king position
                for (i_k = 0; i_k < 8; i_k++) {
                    for (j_k = 0; j_k < 8; j_k++) {
                        if (turn == 'w' && board[i_k][j_k] == wk || turn == 'b' && board[i_k][j_k] == bk) {
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 1) break;
                }

                moves other_king = new moves();
                other_king.compile(board, i_k, j_k, 0);                    	//this king valid moves
                v_map = other_king.vm_ret();								//v_map contains this kings valid moves
                int this_king_check = king_check(0, 0, 0, 0, 1);			//king standing position is checked
                //other_safe.vm_show();
                if (this_king_check==0) {
                    for (int l = 0; l < 8; l++)
                    {for (int m = 0; m < 8; m++)
                        if (v_map[l][m] == true &&color(board[l][m])!=turn&& king_check(i_k, j_k, l, m, 0) == 0)
                            return 1;  //
                        else if(onlyking()!=1)							//others players are also there
                            return 1;

                    }
                    {

                        System.out.println("Stalemate Draw");
                        return -1;
                    }
                }
                if (this_king_check == 1) {
                    if (turn == 'b')
                        System.out.println("White player check black player king");
                    else
                        System.out.println("Black player check white player king");
                }
                if (this_king_check == 1 && checkmate() == 1) {
                    {
                        if (turn == 'b')
                            System.out.println("White player wins");
                        else
                            System.out.println("Black player wins");
                        return -1;
                    }
                }
                return 1;
            }
        }
        //kings closed

        else if (i == f) {                        //same player pieces
            System.out.println("Cannibalism");
            return 0;
        }


        moves valid = new moves();
        valid.compile(board, i_i, i_c, 0);        //valid moves
        v_map=valid.vm_ret();
        int king_ch = king_check(i_i, i_c, f_i, f_c, 0);


        //for black enpassant kill
        if(king_ch==0){
            if	(encom[0][f_c]==true&&board[f_i][f_c]==' '&&board[i_i][i_c]==bp
                    &&i_i==4&&f_i==5&&f_c==i_c-1&&i_c!=0&&i_c>0&&board[i_i][i_c-1]==wp)
                se_move(i_i,i_c,f_i,f_c,i_i,i_c-1);	//enpassant left kill
            if	(encom[0][f_c]==true&&board[f_i][f_c]==' '&&board[i_i][i_c]==bp
                    &&i_i==4&&f_i==5&&f_c==i_c+1&&i_c!=7&&i_c<8&&board[i_i][i_c+1]==wp)
                se_move(i_i,i_c,f_i,f_c,i_i,i_c+1);						//enpassant right kill

            // for white enpassant kill
            if	(encom[1][f_c]==true&&board[f_i][f_c]==' '&&board[i_i][i_c]==wp
                    &&i_i==3&&f_i==2&&f_c==i_c-1&&i_c!=0&&i_c>0&&board[i_i][i_c-1]==bp)
                se_move(i_i,i_c,f_i,f_c,i_i,i_c-1);									//enpassant left kill
            if	(encom[1][f_c]==true&&board[f_i][f_c]==' '&&board[i_i][i_c]==wp
                    &&i_i==3&&f_i==2&&f_c==i_c+1&&i_c!=7&&i_c<8&&board[i_i][i_c+1]==bp)
                se_move(i_i,i_c,f_i,f_c,i_i,i_c+1);									//enpassant right kill

        }

        if (v_map[f_i][f_c] == true && king_ch == 0)            //if valid destination and king is not checked
        {
            move(i_i, i_c, f_i, f_c);
            if(board[f_i][f_c]==wp&&f_i==0) board[f_i][f_c]=wq;
            else if(board[f_i][f_c]==bp&&f_i==7) board[f_i][f_c]=bq;
        }
        else {
            System.out.println("illegal moves");
            return 0;
        }
        /////player turn has been switched
        //turn in following are for other player

        moves other_safe = new moves();
        other_safe.enemy_area(turn, board);//////
        e_map = other_safe.vm_ret();

        int i_k = 0, j_k = 0, flag = 0;
        for (i_k = 0; i_k < 8; i_k++) {
            for (j_k = 0; j_k < 8; j_k++) {
                if (turn == 'w' && board[i_k][j_k] == wk || turn == 'b' && board[i_k][j_k] == bk) {
                    flag = 1;
                    break;
                }
            }
            if (flag == 1) break;
        }
        moves other_king = new moves();
        other_king.compile(board, i_k, j_k, 0);                    //kings valid moves
        v_map = other_king.vm_ret();
        int this_king_check = king_check(0, 0, 0, 0, 1);
        if (this_king_check==0) {
            for (int l = 0; l < 8; l++)
            {for (int m = 0; m < 8; m++)
                if (v_map[l][m] == true && king_check(i_k, j_k, l, m, 0) == 0) return 1;
                else if(onlyking()!=1)							//other players are also there
                    return 1;
            }
            {
                System.out.println("Stalemate Draw");
                return -1;
            }
        }
        if (this_king_check == 1) {
            if (turn == 'b')
                System.out.println("White player check black player king");
            else
                System.out.println("Black player check white player king");
        }
        if (this_king_check == 1 && checkmate() == 1) {
            {
                if (turn == 'b')
                    System.out.println("White player wins");
                else
                    System.out.println("Black player wins");
                return -1;
            }
        }


        return 1;
    }

    int onlyking()
    {	int n=0;
        char colour=turn;

        for(int i=0;i<8;i++)
            for(int j=0;j<8;j++)
                if(color(board[i][j])==colour)
                    n++;

        return n;
    }

    int king_check(int i_i, int i_c, int f_i, int f_c, int type) {            //type = 0 king check with change

        char[][] copy_board = new char[8][8];
        int i, j = 8, flag = 0;
        for (i = 0; i < 8; i++)
            for (j = 0; j < 8; j++)
                copy_board[i][j] = board[i][j];
        moves valid=new moves();
        valid.compile(copy_board, i_i, i_c, 0);
        v_map=valid.vm_ret();
        if (v_map[f_i][f_c] == true && color(copy_board[f_i][f_c]) != turn && type == 0) { //destination is valid with another piece
            copy_board[f_i][f_c] = copy_board[i_i][i_c];
            copy_board[i_i][i_c] = ' ';
        }

        for (i = 0; i < 8; i++) {							//for finding king
            for (j = 0; j < 8; j++) {
                if (turn == 'w' && copy_board[i][j] == wk || turn == 'b' && copy_board[i][j] == bk) {
                    flag = 1;
                    break;
                }
            }
            if (flag == 1) break;
        }
        moves enemy = new moves();
        enemy.enemy_area(turn, copy_board);
        boolean[][] ch_map = new boolean[8][8];
        ch_map = enemy.vm_ret();
        //enemy.vm_show();
        if (ch_map[i][j] == true)            //if king is checked
        {
            // System.out.println("king is checked");
            return 1;
        }                            ////0 for safe   1 for check
        return 0;

    }

    int checkmate() {                            //check for possible saving moves
        int i, j, flag = 1;
        for (i = 0; i < 8; i++)
            for (j = 0; j < 8; j++) {
                if (color(board[i][j]) == turn) {

                    moves temp = new moves();
                    temp.compile(board, i, j, 0);
                    boolean[][] tempo = temp.vm_ret();
                    for (int k = 0; k < 8; k++)
                        for (int l = 0; l < 8; l++) {
                            {
                                if (tempo[k][l] == true) {
                                    flag = king_check(i, j, k, l, 0);
                                    //System.out.println(i+" "+j+" "+k+" "+l+"flag = "+flag);
                                    if (flag == 0) return 0;
                                }
                            }
                        }
                }
            }                                                //return 0 for safe   1 for check mate
        return 1;
    }

    void start() {

        String s;
        @SuppressWarnings("resource")
        Scanner inp = new Scanner(System.in);
        do {
            show();
            System.out.println();
            if (turn == 'w') System.out.println("White player turn");
            else System.out.println("Black player turn");
            System.out.println("Console : ");
            s = inp.nextLine();
            s = s.toUpperCase();
            int check = convert(s);
            if (check == 0) continue;
            if (check == -1) break;
            if (check == 2) {
                System.out.println("Castle not possible");
                continue;
            }
        } while (true);
        System.out.print("The End !!!");

    }
}


