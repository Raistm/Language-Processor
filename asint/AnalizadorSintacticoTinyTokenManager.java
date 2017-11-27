/* AnalizadorSintacticoTinyTokenManager.java */
/* Generated By:JavaCC: Do not edit this line. AnalizadorSintacticoTinyTokenManager.java */
package asint;
import programa.Programa.Prog;
import programa.Programa.Dec;
import programa.Programa.Inst;
import programa.Programa.Case;
import programa.Programa.CampoReg;
import programa.Programa.TDefinido;
import programa.Programa.FParam;
import programa.Programa.Exp;
import asint.ASTOps.Lista;

/** Token Manager. */
@SuppressWarnings("unused")public class AnalizadorSintacticoTinyTokenManager implements AnalizadorSintacticoTinyConstants {

  /** Debug output. */
  public  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private final int jjStopStringLiteralDfa_0(int pos, long active0, long active1){
   switch (pos)
   {
      case 0:
         if ((active1 & 0x8L) != 0L)
            return 22;
         if ((active0 & 0x8000000000000000L) != 0L)
            return 1;
         return -1;
      default :
         return -1;
   }
}
private final int jjStartNfa_0(int pos, long active0, long active1){
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0, active1), pos + 1);
}
private int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
private int jjMoveStringLiteralDfa0_0(){
   switch(curChar)
   {
      case 33:
         jjmatchedKind = 65;
         return jjMoveStringLiteralDfa1_0(0x40000000000000L, 0x0L);
      case 37:
         return jjStopAtPos(0, 64);
      case 38:
         jjmatchedKind = 45;
         return jjMoveStringLiteralDfa1_0(0x4000000000000000L, 0x0L);
      case 40:
         return jjStopAtPos(0, 42);
      case 41:
         return jjStopAtPos(0, 43);
      case 42:
         return jjStopAtPos(0, 46);
      case 43:
         return jjStopAtPos(0, 60);
      case 44:
         return jjStopAtPos(0, 44);
      case 45:
         jjmatchedKind = 61;
         return jjMoveStringLiteralDfa1_0(0x0L, 0x10L);
      case 46:
         return jjStartNfaWithStates_0(0, 67, 22);
      case 47:
         return jjStartNfaWithStates_0(0, 63, 1);
      case 58:
         return jjStopAtPos(0, 52);
      case 59:
         return jjStopAtPos(0, 41);
      case 60:
         jjmatchedKind = 56;
         return jjMoveStringLiteralDfa1_0(0x80000000000000L, 0x0L);
      case 61:
         jjmatchedKind = 51;
         return jjMoveStringLiteralDfa1_0(0x220000000000000L, 0x0L);
      case 62:
         return jjStopAtPos(0, 58);
      case 91:
         jjmatchedKind = 47;
         return jjMoveStringLiteralDfa1_0(0x0L, 0x20L);
      case 93:
         return jjStopAtPos(0, 48);
      case 95:
         return jjStopAtPos(0, 66);
      case 123:
         return jjStopAtPos(0, 49);
      case 124:
         return jjMoveStringLiteralDfa1_0(0x800000000000000L, 0x0L);
      case 125:
         return jjStopAtPos(0, 50);
      default :
         return jjMoveNfa_0(0, 0);
   }
}
private int jjMoveStringLiteralDfa1_0(long active0, long active1){
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(0, active0, active1);
      return 1;
   }
   switch(curChar)
   {
      case 38:
         if ((active0 & 0x4000000000000000L) != 0L)
            return jjStopAtPos(1, 62);
         break;
      case 61:
         if ((active0 & 0x20000000000000L) != 0L)
            return jjStopAtPos(1, 53);
         else if ((active0 & 0x40000000000000L) != 0L)
            return jjStopAtPos(1, 54);
         else if ((active0 & 0x80000000000000L) != 0L)
            return jjStopAtPos(1, 55);
         break;
      case 62:
         if ((active0 & 0x200000000000000L) != 0L)
            return jjStopAtPos(1, 57);
         else if ((active1 & 0x10L) != 0L)
            return jjStopAtPos(1, 68);
         break;
      case 93:
         if ((active1 & 0x20L) != 0L)
            return jjStopAtPos(1, 69);
         break;
      case 124:
         if ((active0 & 0x800000000000000L) != 0L)
            return jjStopAtPos(1, 59);
         break;
      default :
         break;
   }
   return jjStartNfa_0(0, active0, active1);
}
private int jjStartNfaWithStates_0(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_0(state, pos + 1);
}
static final long[] jjbitVec0 = {
   0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
};
private int jjMoveNfa_0(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 139;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 22:
                  if ((0x3ff000000000000L & l) != 0L)
                     { jjCheckNAddTwoStates(20, 21); }
                  if ((0x3fe000000000000L & l) != 0L)
                  {
                     if (kind > 36)
                        kind = 36;
                  }
                  else if (curChar == 48)
                  {
                     if (kind > 36)
                        kind = 36;
                  }
                  break;
               case 0:
                  if ((0x3ff000000000000L & l) != 0L)
                     { jjCheckNAddTwoStates(17, 18); }
                  else if ((0x100002700L & l) != 0L)
                  {
                     if (kind > 4)
                        kind = 4;
                  }
                  else if (curChar == 34)
                     jjstateSet[jjnewStateCnt++] = 27;
                  else if (curChar == 39)
                     jjstateSet[jjnewStateCnt++] = 24;
                  else if (curChar == 46)
                     { jjCheckNAddStates(0, 2); }
                  else if (curChar == 47)
                     jjstateSet[jjnewStateCnt++] = 1;
                  if ((0x3fe000000000000L & l) != 0L)
                  {
                     if (kind > 34)
                        kind = 34;
                     { jjCheckNAddStates(3, 6); }
                  }
                  else if (curChar == 48)
                  {
                     if (kind > 34)
                        kind = 34;
                     { jjCheckNAddTwoStates(129, 132); }
                  }
                  break;
               case 1:
                  if (curChar != 47)
                     break;
                  if (kind > 5)
                     kind = 5;
                  { jjCheckNAdd(2); }
                  break;
               case 2:
                  if ((0xfffffffffffffbffL & l) == 0L)
                     break;
                  if (kind > 5)
                     kind = 5;
                  { jjCheckNAdd(2); }
                  break;
               case 3:
                  if (curChar == 47)
                     jjstateSet[jjnewStateCnt++] = 1;
                  break;
               case 16:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 33)
                     kind = 33;
                  jjstateSet[jjnewStateCnt++] = 16;
                  break;
               case 17:
                  if ((0x3ff000000000000L & l) != 0L)
                     { jjCheckNAddTwoStates(17, 18); }
                  break;
               case 18:
                  if ((0x3fe000000000000L & l) != 0L && kind > 35)
                     kind = 35;
                  break;
               case 19:
                  if (curChar == 46)
                     { jjCheckNAddStates(0, 2); }
                  break;
               case 20:
                  if ((0x3ff000000000000L & l) != 0L)
                     { jjCheckNAddTwoStates(20, 21); }
                  break;
               case 21:
                  if ((0x3fe000000000000L & l) != 0L && kind > 36)
                     kind = 36;
                  break;
               case 23:
                  if (curChar == 39)
                     jjstateSet[jjnewStateCnt++] = 24;
                  break;
               case 24:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 25;
                  break;
               case 25:
                  if (curChar == 39 && kind > 39)
                     kind = 39;
                  break;
               case 26:
                  if (curChar == 34)
                     jjstateSet[jjnewStateCnt++] = 27;
                  break;
               case 28:
                  if ((0x3ff000000000000L & l) != 0L)
                     { jjAddStates(7, 8); }
                  break;
               case 29:
                  if (curChar == 34 && kind > 40)
                     kind = 40;
                  break;
               case 122:
                  if (curChar == 45)
                     { jjAddStates(9, 11); }
                  break;
               case 123:
                  if ((0x3fe000000000000L & l) == 0L)
                     break;
                  if (kind > 37)
                     kind = 37;
                  { jjCheckNAdd(124); }
                  break;
               case 124:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 37)
                     kind = 37;
                  { jjCheckNAdd(124); }
                  break;
               case 125:
                  if (curChar == 48 && kind > 37)
                     kind = 37;
                  break;
               case 126:
                  if ((0x3fe000000000000L & l) == 0L)
                     break;
                  if (kind > 34)
                     kind = 34;
                  { jjCheckNAddStates(3, 6); }
                  break;
               case 127:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 34)
                     kind = 34;
                  { jjCheckNAdd(127); }
                  break;
               case 128:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 38)
                     kind = 38;
                  { jjCheckNAddStates(12, 14); }
                  break;
               case 129:
                  if (curChar == 46)
                     { jjCheckNAddStates(15, 17); }
                  break;
               case 130:
                  if ((0x3ff000000000000L & l) != 0L)
                     { jjCheckNAddTwoStates(130, 131); }
                  break;
               case 131:
                  if ((0x3fe000000000000L & l) == 0L)
                     break;
                  if (kind > 38)
                     kind = 38;
                  { jjCheckNAddTwoStates(129, 132); }
                  break;
               case 133:
                  if (curChar == 45)
                     { jjAddStates(18, 20); }
                  break;
               case 134:
                  if ((0x3fe000000000000L & l) == 0L)
                     break;
                  if (kind > 38)
                     kind = 38;
                  { jjCheckNAddTwoStates(132, 135); }
                  break;
               case 135:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 38)
                     kind = 38;
                  { jjCheckNAddTwoStates(132, 135); }
                  break;
               case 136:
                  if (curChar != 48)
                     break;
                  if (kind > 38)
                     kind = 38;
                  { jjCheckNAdd(132); }
                  break;
               case 137:
                  if (curChar != 48)
                     break;
                  if (kind > 38)
                     kind = 38;
                  { jjCheckNAddTwoStates(129, 132); }
                  break;
               case 138:
                  if (curChar != 48)
                     break;
                  if (kind > 34)
                     kind = 34;
                  { jjCheckNAddTwoStates(129, 132); }
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0x7fffffe07fffffeL & l) != 0L)
                  {
                     if (kind > 33)
                        kind = 33;
                     { jjCheckNAdd(16); }
                  }
                  if ((0x2000000020L & l) != 0L)
                     { jjAddStates(21, 24); }
                  else if ((0x8000000080000L & l) != 0L)
                     { jjAddStates(25, 27); }
                  else if ((0x400000004000L & l) != 0L)
                     { jjAddStates(28, 29); }
                  else if ((0x800000008L & l) != 0L)
                     { jjAddStates(30, 32); }
                  else if ((0x1000000010L & l) != 0L)
                     { jjAddStates(33, 35); }
                  else if ((0x80000000800000L & l) != 0L)
                     { jjAddStates(36, 38); }
                  else if ((0x400000004L & l) != 0L)
                     { jjAddStates(39, 40); }
                  else if ((0x20000000200L & l) != 0L)
                     { jjAddStates(41, 42); }
                  else if ((0x4000000040L & l) != 0L)
                     { jjAddStates(43, 44); }
                  else if ((0x10000000100000L & l) != 0L)
                     { jjAddStates(45, 47); }
                  else if ((0x4000000040000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 12;
                  else if ((0x1000000010000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 8;
                  else if ((0x40000000400000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 5;
                  break;
               case 2:
                  if (kind > 5)
                     kind = 5;
                  jjstateSet[jjnewStateCnt++] = 2;
                  break;
               case 4:
                  if ((0x40000000400000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 5;
                  break;
               case 5:
                  if ((0x200000002L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 6;
                  break;
               case 6:
                  if ((0x4000000040000L & l) != 0L && kind > 11)
                     kind = 11;
                  break;
               case 7:
                  if ((0x1000000010000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 8;
                  break;
               case 8:
                  if ((0x4000000040000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 9;
                  break;
               case 9:
                  if ((0x800000008000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 10;
                  break;
               case 10:
                  if ((0x800000008L & l) != 0L && kind > 14)
                     kind = 14;
                  break;
               case 11:
                  if ((0x4000000040000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 12;
                  break;
               case 12:
                  if ((0x2000000020L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 13;
                  break;
               case 13:
                  if ((0x200000002L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 14;
                  break;
               case 14:
                  if ((0x1000000010L & l) != 0L && kind > 24)
                     kind = 24;
                  break;
               case 15:
               case 16:
                  if ((0x7fffffe07fffffeL & l) == 0L)
                     break;
                  if (kind > 33)
                     kind = 33;
                  { jjCheckNAdd(16); }
                  break;
               case 24:
                  if ((0x7fffffe07fffffeL & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 25;
                  break;
               case 27:
               case 28:
                  if ((0x7fffffe07fffffeL & l) != 0L)
                     { jjCheckNAddTwoStates(28, 29); }
                  break;
               case 30:
                  if ((0x10000000100000L & l) != 0L)
                     { jjAddStates(45, 47); }
                  break;
               case 31:
                  if ((0x4000000040000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 32;
                  break;
               case 32:
                  if ((0x20000000200000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 33;
                  break;
               case 33:
                  if ((0x2000000020L & l) != 0L && kind > 6)
                     kind = 6;
                  break;
               case 34:
                  if ((0x200000002000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 35;
                  break;
               case 35:
                  if ((0x1000000010000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 36;
                  break;
               case 36:
                  if ((0x2000000020L & l) != 0L && kind > 10)
                     kind = 10;
                  break;
               case 37:
                  if ((0x10000000100L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 38;
                  break;
               case 38:
                  if ((0x2000000020L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 39;
                  break;
               case 39:
                  if ((0x400000004000L & l) != 0L && kind > 31)
                     kind = 31;
                  break;
               case 40:
                  if ((0x4000000040L & l) != 0L)
                     { jjAddStates(43, 44); }
                  break;
               case 41:
                  if ((0x200000002L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 42;
                  break;
               case 42:
                  if ((0x100000001000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 43;
                  break;
               case 43:
                  if ((0x8000000080000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 44;
                  break;
               case 44:
                  if ((0x2000000020L & l) != 0L && kind > 7)
                     kind = 7;
                  break;
               case 45:
                  if ((0x100000001000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 46;
                  break;
               case 46:
                  if ((0x800000008000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 47;
                  break;
               case 47:
                  if ((0x200000002L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 48;
                  break;
               case 48:
                  if ((0x10000000100000L & l) != 0L && kind > 27)
                     kind = 27;
                  break;
               case 49:
                  if ((0x20000000200L & l) != 0L)
                     { jjAddStates(41, 42); }
                  break;
               case 50:
                  if ((0x400000004000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 51;
                  break;
               case 51:
                  if ((0x10000000100000L & l) != 0L && kind > 8)
                     kind = 8;
                  break;
               case 52:
                  if ((0x4000000040L & l) != 0L && kind > 30)
                     kind = 30;
                  break;
               case 53:
                  if ((0x400000004L & l) != 0L)
                     { jjAddStates(39, 40); }
                  break;
               case 54:
                  if ((0x800000008000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 55;
                  break;
               case 55:
                  if ((0x800000008000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 56;
                  break;
               case 56:
                  if ((0x100000001000L & l) != 0L && kind > 9)
                     kind = 9;
                  break;
               case 57:
                  if ((0x4000000040000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 58;
                  break;
               case 58:
                  if ((0x2000000020L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 59;
                  break;
               case 59:
                  if ((0x200000002L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 60;
                  break;
               case 60:
                  if ((0x80000000800L & l) != 0L && kind > 22)
                     kind = 22;
                  break;
               case 61:
                  if ((0x80000000800000L & l) != 0L)
                     { jjAddStates(36, 38); }
                  break;
               case 62:
                  if ((0x10000000100L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 63;
                  break;
               case 63:
                  if ((0x20000000200L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 64;
                  break;
               case 64:
                  if ((0x100000001000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 65;
                  break;
               case 65:
                  if ((0x2000000020L & l) != 0L && kind > 12)
                     kind = 12;
                  break;
               case 66:
                  if ((0x20000000200L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 67;
                  break;
               case 67:
                  if ((0x10000000100000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 68;
                  break;
               case 68:
                  if ((0x10000000100L & l) != 0L && kind > 16)
                     kind = 16;
                  break;
               case 69:
                  if ((0x4000000040000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 70;
                  break;
               case 70:
                  if ((0x20000000200L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 71;
                  break;
               case 71:
                  if ((0x10000000100000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 72;
                  break;
               case 72:
                  if ((0x2000000020L & l) != 0L && kind > 25)
                     kind = 25;
                  break;
               case 73:
                  if ((0x1000000010L & l) != 0L)
                     { jjAddStates(33, 35); }
                  break;
               case 74:
                  if ((0x800000008000L & l) != 0L && kind > 13)
                     kind = 13;
                  break;
               case 75:
                  if ((0x2000000020L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 76;
                  break;
               case 76:
                  if ((0x100000001000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 77;
                  break;
               case 77:
                  if ((0x2000000020L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 78;
                  break;
               case 78:
                  if ((0x10000000100000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 79;
                  break;
               case 79:
                  if ((0x2000000020L & l) != 0L && kind > 18)
                     kind = 18;
                  break;
               case 80:
                  if ((0x2000000020L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 81;
                  break;
               case 81:
                  if ((0x4000000040L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 82;
                  break;
               case 82:
                  if ((0x200000002L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 83;
                  break;
               case 83:
                  if ((0x20000000200000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 84;
                  break;
               case 84:
                  if ((0x100000001000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 85;
                  break;
               case 85:
                  if ((0x10000000100000L & l) != 0L && kind > 23)
                     kind = 23;
                  break;
               case 86:
                  if ((0x800000008L & l) != 0L)
                     { jjAddStates(30, 32); }
                  break;
               case 87:
                  if ((0x200000002L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 88;
                  break;
               case 88:
                  if ((0x100000001000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 89;
                  break;
               case 89:
                  if ((0x100000001000L & l) != 0L && kind > 15)
                     kind = 15;
                  break;
               case 90:
                  if ((0x200000002L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 91;
                  break;
               case 91:
                  if ((0x8000000080000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 92;
                  break;
               case 92:
                  if ((0x2000000020L & l) != 0L && kind > 21)
                     kind = 21;
                  break;
               case 93:
                  if ((0x10000000100L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 94;
                  break;
               case 94:
                  if ((0x200000002L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 95;
                  break;
               case 95:
                  if ((0x4000000040000L & l) != 0L && kind > 26)
                     kind = 26;
                  break;
               case 96:
                  if ((0x400000004000L & l) != 0L)
                     { jjAddStates(28, 29); }
                  break;
               case 97:
                  if ((0x2000000020L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 98;
                  break;
               case 98:
                  if ((0x80000000800000L & l) != 0L && kind > 17)
                     kind = 17;
                  break;
               case 99:
                  if ((0x20000000200000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 100;
                  break;
               case 100:
                  if ((0x100000001000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 101;
                  break;
               case 101:
                  if ((0x100000001000L & l) != 0L && kind > 19)
                     kind = 19;
                  break;
               case 102:
                  if ((0x8000000080000L & l) != 0L)
                     { jjAddStates(25, 27); }
                  break;
               case 103:
                  if ((0x10000000100000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 104;
                  break;
               case 104:
                  if ((0x4000000040000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 105;
                  break;
               case 105:
                  if ((0x20000000200000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 106;
                  break;
               case 106:
                  if ((0x800000008L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 107;
                  break;
               case 107:
                  if ((0x10000000100000L & l) != 0L && kind > 20)
                     kind = 20;
                  break;
               case 108:
                  if ((0x10000000100000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 109;
                  break;
               case 109:
                  if ((0x4000000040000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 110;
                  break;
               case 110:
                  if ((0x20000000200L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 111;
                  break;
               case 111:
                  if ((0x400000004000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 112;
                  break;
               case 112:
                  if ((0x8000000080L & l) != 0L && kind > 28)
                     kind = 28;
                  break;
               case 113:
                  if ((0x80000000800000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 114;
                  break;
               case 114:
                  if ((0x20000000200L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 115;
                  break;
               case 115:
                  if ((0x10000000100000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 116;
                  break;
               case 116:
                  if ((0x800000008L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 117;
                  break;
               case 117:
                  if ((0x10000000100L & l) != 0L && kind > 29)
                     kind = 29;
                  break;
               case 118:
                  if ((0x2000000020L & l) != 0L)
                     { jjAddStates(21, 24); }
                  break;
               case 119:
                  if ((0x100000001000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 120;
                  break;
               case 120:
                  if ((0x8000000080000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 121;
                  break;
               case 121:
                  if ((0x2000000020L & l) != 0L && kind > 32)
                     kind = 32;
                  break;
               case 132:
                  if ((0x2000000020L & l) != 0L)
                     { jjAddStates(18, 20); }
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 2:
                  if ((jjbitVec0[i2] & l2) == 0L)
                     break;
                  if (kind > 5)
                     kind = 5;
                  jjstateSet[jjnewStateCnt++] = 2;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 139 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
static final int[] jjnextStates = {
   20, 21, 22, 127, 128, 129, 132, 28, 29, 122, 123, 125, 128, 129, 132, 130, 
   131, 137, 133, 134, 136, 119, 122, 123, 125, 103, 108, 113, 97, 99, 87, 90, 
   93, 74, 75, 80, 62, 66, 69, 54, 57, 50, 52, 41, 45, 31, 34, 37, 
};

/** Token literal values. */
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, null, null, null, null, null, null, null, null, 
null, null, null, null, null, null, null, null, null, null, null, null, null, null, 
null, null, null, null, null, null, null, null, null, null, null, null, null, null, 
"\73", "\50", "\51", "\54", "\46", "\52", "\133", "\135", "\173", "\175", "\75", 
"\72", "\75\75", "\41\75", "\74\75", "\74", "\75\76", "\76", "\174\174", "\53", 
"\55", "\46\46", "\57", "\45", "\41", "\137", "\56", "\55\76", "\133\135", };
protected Token jjFillToken()
{
   final Token t;
   final String curTokenImage;
   final int beginLine;
   final int endLine;
   final int beginColumn;
   final int endColumn;
   String im = jjstrLiteralImages[jjmatchedKind];
   curTokenImage = (im == null) ? input_stream.GetImage() : im;
   beginLine = input_stream.getBeginLine();
   beginColumn = input_stream.getBeginColumn();
   endLine = input_stream.getEndLine();
   endColumn = input_stream.getEndColumn();
   t = Token.newToken(jjmatchedKind, curTokenImage);

   t.beginLine = beginLine;
   t.endLine = endLine;
   t.beginColumn = beginColumn;
   t.endColumn = endColumn;

   return t;
}

int curLexState = 0;
int defaultLexState = 0;
int jjnewStateCnt;
int jjround;
int jjmatchedPos;
int jjmatchedKind;

/** Get the next Token. */
public Token getNextToken() 
{
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {
   try
   {
      curChar = input_stream.BeginToken();
   }
   catch(java.io.IOException e)
   {
      jjmatchedKind = 0;
      jjmatchedPos = -1;
      matchedToken = jjFillToken();
      return matchedToken;
   }

   jjmatchedKind = 0x7fffffff;
   jjmatchedPos = 0;
   curPos = jjMoveStringLiteralDfa0_0();
   if (jjmatchedKind != 0x7fffffff)
   {
      if (jjmatchedPos + 1 < curPos)
         input_stream.backup(curPos - jjmatchedPos - 1);
      if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
      {
         matchedToken = jjFillToken();
         return matchedToken;
      }
      else
      {
         continue EOFLoop;
      }
   }
   int error_line = input_stream.getEndLine();
   int error_column = input_stream.getEndColumn();
   String error_after = null;
   boolean EOFSeen = false;
   try { input_stream.readChar(); input_stream.backup(1); }
   catch (java.io.IOException e1) {
      EOFSeen = true;
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
      if (curChar == '\n' || curChar == '\r') {
         error_line++;
         error_column = 0;
      }
      else
         error_column++;
   }
   if (!EOFSeen) {
      input_stream.backup(1);
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
   }
   throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
  }
}

private void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
private void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
private void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}

private void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}

    /** Constructor. */
    public AnalizadorSintacticoTinyTokenManager(SimpleCharStream stream){

      if (SimpleCharStream.staticFlag)
            throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");

    input_stream = stream;
  }

  /** Constructor. */
  public AnalizadorSintacticoTinyTokenManager (SimpleCharStream stream, int lexState){
    ReInit(stream);
    SwitchTo(lexState);
  }

  /** Reinitialise parser. */
  public void ReInit(SimpleCharStream stream)
  {
    jjmatchedPos = jjnewStateCnt = 0;
    curLexState = defaultLexState;
    input_stream = stream;
    ReInitRounds();
  }

  private void ReInitRounds()
  {
    int i;
    jjround = 0x80000001;
    for (i = 139; i-- > 0;)
      jjrounds[i] = 0x80000000;
  }

  /** Reinitialise parser. */
  public void ReInit(SimpleCharStream stream, int lexState)
  {
    ReInit(stream);
    SwitchTo(lexState);
  }

  /** Switch to specified lex state. */
  public void SwitchTo(int lexState)
  {
    if (lexState >= 1 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
    else
      curLexState = lexState;
  }

/** Lexer state names. */
public static final String[] lexStateNames = {
   "DEFAULT",
};
static final long[] jjtoToken = {
   0xffffffffffffffc1L, 0x3fL, 
};
static final long[] jjtoSkip = {
   0x30L, 0x0L, 
};
    protected SimpleCharStream  input_stream;

    private final int[] jjrounds = new int[139];
    private final int[] jjstateSet = new int[2 * 139];

    
    protected char curChar;
}
