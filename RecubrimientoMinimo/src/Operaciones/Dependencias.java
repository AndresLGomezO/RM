package Operaciones;

import java.util.Iterator;

public class Dependencias {

	  private Arreglos x;
	  private Arreglos y;


	  public Dependencias(Arreglos x, Arreglos y) {
	    this.x = x;
	    this.y = y;
	  }

	  public Arreglos giveX(){
	    return x;
	  }

	  public Arreglos giveY(){
	    return y;
	  }

	  public String toString(){
	    return(x.toString() + " -> " + y.toString());
	  }

	  public String toHumanFormat(){
	    return(x.toFPlusFormat() + "->" + y.toFPlusFormat());
	  }

	  public boolean equiv(Dependencias f){
	    return(this.giveX().equiv(f.giveX())&&this.giveY().equiv(f.giveY()));
	  }

	  public Dependencias copy(){
	    return (new Dependencias(this.giveX().copy(), this.giveY().copy()));
	  }

	  public boolean xIsSuperKey(Arreglos relation, Funciones deps){
	    //normalization condition FNBC
		  Arreglos sks = relation.bruteKeys(deps);
	    //if(sks.includes(this.giveX())) // or contains?
	    if(sks.contains(this.giveX())) // or includes?
	      return true;
	    else
	      return false;
	  }

	  public boolean yIsPrime2(Arreglos relation, Funciones deps){
	    //normalization condition 3FN
	    //y is included in a ck
	    boolean flag = false;
	    Arreglos ccs = relation.candKeys(deps);
	    Iterator i = ccs.iterator();
	    while(i.hasNext()){
	    	Arreglos c = (Arreglos)i.next();
	      if(c.includes(this.giveY()))
	        flag = true;
	    }
	    return flag;
	  }

	  public boolean yIsPrime(Arreglos relation, Funciones deps){
	    //normalization condition 3FN
	    //y OR ITS PARTS are included in a ck
	    boolean flag = true;
	    Arreglos ccs = relation.candKeys(deps);
	    Arreglos at = ccs.atomize();

	      Iterator j = this.giveY().iterator();
	      while(j.hasNext()){
	        Object o = j.next();
	        if(!at.includes(o))
	          flag = false;
	      }

	    return flag;
	  }

	  public boolean xIsSub(Arreglos relation, Funciones deps){
	  //normalization condition 2FN
	  //x is included in a ck
	    boolean flag = false;
	    Arreglos ccs = relation.candKeys(deps);
	    Iterator i = ccs.iterator();
	    while(i.hasNext()){
	    	Arreglos c = (Arreglos)i.next();
	      if(c.includes(this.giveX()))
	        flag = true;
	    }
	    return flag;
	  }

	  public boolean failsFNBC(Arreglos ats, Funciones deps){
	    return !(this.xIsSuperKey(ats, deps)||this.isTrivial());
	  }

	  public boolean fails3FN(Arreglos ats, Funciones deps){
	    return !(!this.failsFNBC(ats, deps)||this.yIsPrime(ats, deps));
	  }

	  public boolean fails2FN(Arreglos ats, Funciones deps){
	    return !(!this.fails3FN(ats, deps)||!this.xIsSub(ats, deps));
	  }

	  public String getNormalForm(Arreglos ats, Funciones deps){
	    if(!this.failsFNBC(ats, deps))
	      return "FNBC";
	    else
	      if(!this.fails3FN(ats, deps))
	        return "3FN";
	      else
	        if(!this.fails2FN(ats, deps))
	          return "2FN";
	        else
	          return "1FN";
	  }

	  public boolean isTrivial(){
	  //or contains?
	  //trivial deps are automatically in FNBC
	    return this.giveX().includes(this.giveY());
	  }

	  public boolean isRelevant(Arreglos rel){
	  //returns true if all attributes in this fd
	  //are present in the relation

	    boolean ret = true;

	    Arreglos all = this.giveX().unionSafe(this.giveY());


	      Iterator i = all.iterator();
	      while(i.hasNext()){
	    	  Arreglos c1 = new Arreglos(i.next());
	        if(!rel.includes(c1))
	          ret = false;
	      }


	    return ret;
	  }


	  public Arreglos toRelation(){
	    return this.giveX().atomize().unionSafe(this.giveY().atomize());
	  }

	  public int size(){
	    return this.giveX().size()+this.giveY().size();
	  }

	}



