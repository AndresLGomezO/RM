package Operaciones;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class Arreglos implements Set {

  protected ArrayList DependenciasFuncionales;

  public Arreglos() {
	  DependenciasFuncionales = new ArrayList();
  }

  public Arreglos(Object o) {
	  DependenciasFuncionales = new ArrayList();
    this.add(o);
  }

  //-----------------------------------------start of interface set
  public int size() {
    //throw new java.lang.UnsupportedOperationException("Method size() not yet implemented.");
    return DependenciasFuncionales.size();
  }

  public boolean isEmpty() {
    //throw new java.lang.UnsupportedOperationException("Method isEmpty() not yet implemented.");
    return DependenciasFuncionales.isEmpty();
  }

  public boolean contains(Object o) {
    //throw new java.lang.UnsupportedOperationException("Method contains() not yet implemented.");
    //return vector.contains(o);
    Iterator i = this.iterator();
    while(i.hasNext()){
      if(i.next().toString().compareTo(o.toString()) == 0)
        return true;
    }
    return false;
  }

  public boolean containsNew(Object o) {
    //throw new java.lang.UnsupportedOperationException("Method contains() not yet implemented.");
    //return vector.contains(o);
    if(!o.getClass().toString().endsWith("Arreglos")){
      Iterator i = this.iterator();
      while(i.hasNext()){
        if(i.next().toString().compareTo(o.toString()) == 0)
          return true;
      }
      return false;
    }
    else{
      return false;
    }
  }

  public Iterator iterator() {
    //throw new java.lang.UnsupportedOperationException("Method iterator() not yet implemented.");
    return DependenciasFuncionales.iterator();
  }

  public Object[] toArray() {
    //throw new java.lang.UnsupportedOperationException("Method toArray() not yet implemented.");
    return DependenciasFuncionales.toArray();
  }

  public Object[] toArray(Object[] a) {
    //throw new java.lang.UnsupportedOperationException("Method toArray() not yet implemented.");
    return DependenciasFuncionales.toArray(a);
  }

  public boolean add(Object o) {
    //throw new java.lang.UnsupportedOperationException("Method add() not yet implemented.");
    if(!this.contains(o))
      return DependenciasFuncionales.add(o);
    else
      return true;
  }

  public boolean remove(Object o) {
    //throw new java.lang.UnsupportedOperationException("Method remove() not yet implemented.");
    return DependenciasFuncionales.remove(o);
    /*Iterator i = this.iterator();
    Object del = null;
    while(i.hasNext()){
      Object ob = i.next();
      if(ob.toString().compareTo(o.toString()) == 0){
        del = ob;
      }
    }
    if(del != null)
      return vector.remove(del);
    else
      return false;*/
  }

  public boolean containsAll(Collection c) {
    //throw new java.lang.UnsupportedOperationException("Method containsAll() not yet implemented.");
    return DependenciasFuncionales.containsAll(c);
  }

  public boolean addAll(Collection c) {
    //throw new java.lang.UnsupportedOperationException("Method addAll() not yet implemented.");
    return DependenciasFuncionales.addAll(c);
  }

  public boolean retainAll(Collection c) {
    //throw new java.lang.UnsupportedOperationException("Method retainAll() not yet implemented.");
    return DependenciasFuncionales.retainAll(c);
  }

  public boolean removeAll(Collection c) {
    //throw new java.lang.UnsupportedOperationException("Method removeAll() not yet implemented.");
    return DependenciasFuncionales.removeAll(c);
  }

  public void clear() {
    //throw new java.lang.UnsupportedOperationException("Method clear() not yet implemented.");
	  DependenciasFuncionales.clear();
  }

  public boolean equals(Object o) {
    //throw new java.lang.UnsupportedOperationException("Method equals() not yet implemented.");
    return DependenciasFuncionales.equals(o);
  }

  //----------------------------------------------------end of interface set

  /*public Arreglos sort(){
    Arreglos ret = new Arreglos;

  }*/

  public boolean equiv(Arreglos s){
    return(this.includes(s) && s.includes(this));
  }

  public boolean includes(Arreglos s){
    Iterator i = s.iterator();
    boolean flag = true;
    while(i.hasNext()){
      if(!this.contains(i.next()))
        flag = false;
    }
    return flag;
  }

  public boolean includes(Object o){
    Arreglos s = new Arreglos();
    s.add(o);
    return this.includes(s);

  }

  public Arreglos union(Arreglos s){
    //warning: overwrites this object
    Iterator i = s.iterator();
    //Arreglos cs = this.copy();
    while(i.hasNext()){
      Object o = i.next();
      if(!this.contains(o))
        this.add(o);
    }
    return this;
  }

  public Arreglos unionSafe(Arreglos s){
    Arreglos ret = this.copy();
    Iterator i = s.iterator();
    //Arreglos cs = this.copy();
    while(i.hasNext()){
      Object o = i.next();
      if(!ret.contains(o))
        ret.add(o);
    }
    return ret;
  }

  public Arreglos intersect(Arreglos s){
  //returns a new set
    Iterator i = s.iterator();
    Arreglos cs = new Arreglos();
    while(i.hasNext()){
      Object o = i.next();
      if(s.contains(o) && this.contains(o))
        cs.add(o);
    }
    return cs;
  }


  public Arreglos minus(Arreglos s){
  //returns a new set
    Arreglos ret = new Arreglos();
    Iterator i = this.iterator();
    while(i.hasNext()){
      Object n = i.next();
      if(!s.includes(n))
        ret.add(n);
    }
    return ret;
  }

  public Arreglos minus(Object ob){
    Arreglos s = new Arreglos();
    s.add(ob);
    return this.minus(s);
  }

  public Object elementAt(int pos){
    return DependenciasFuncionales.get(pos);
  }


  public Arreglos reduction(){
    //returns a new set
    Arreglos r = new Arreglos();

    for(int i = 0; i < this.size(); i++){
      Arreglos c = this.minus(this.elementAt(i));
      if(!c.isEmpty()){
        r.add(c);
        Arreglos re = c.reduction();
        if(!r.includes(re))
          r.union(re);
      }
    }

    return r;
  }

  public String toString(){
    return DependenciasFuncionales.toString();
  }

  public Arreglos copy(){
    Iterator i = this.iterator();
    Arreglos c = new Arreglos();
    while(i.hasNext()){
      c.add(i.next());
    }
    return c;
  }

  public Arreglos min(){
  //returns a new set
    Iterator i = this.iterator();
    Arreglos c = new Arreglos();
    int min = 1000000;
    while(i.hasNext()){
      Arreglos n = (Arreglos)i.next();
      if(n.size()<min){
        c = n;
        min = n.size();
      }
    }
    return c;
  }

  public Arreglos closure(Funciones deps){//NO CLAUSURA PARA X COMPUESTO
  //this method returns a new set
	  Arreglos cl = this.copy();
      Arreglos cl2 = new Arreglos(); //vacio

      while(!cl.equiv(cl2)){
        cl2 = cl.copy();
        Iterator i = deps.iterator();
        while(i.hasNext()){
          Dependencias d = (Dependencias)i.next();

          if( cl.includes(d.giveX()) ){
            //Arreglos tmp = cl.copy();
            cl.union(d.giveY());
            //cl = (Arreglos)
          }
        }

      }
      return cl;
  }

  public Arreglos bruteKeys(Funciones deps){
    Arreglos all = this.reduction();
    Arreglos keys = new Arreglos();
    Iterator i = all.iterator();
    while(i.hasNext()){
      Arreglos n = (Arreglos)i.next();
      if(n.closure(deps).equiv(this))
        keys.add(n);
    }
    //The whole relation is always a superkey!!!
    keys.add(this);
    return keys;
  }

  public Arreglos superKeys(Funciones deps){
  //NOT WORKING!!!
	  Arreglos mis = this.missing(deps);
	  Arreglos right = deps.yAts();
	  Arreglos left = deps.xAts();
	  Arreglos inter = left.intersect(right);
	  Arreglos leftOnly = left.minus(inter);
    //Arreglos candAt = mis.union(leftOnly);
	  Arreglos candAt = leftOnly.union(mis);
    //Arreglos candAtParts = candAt.reduction();
    Iterator i = candAt.iterator();
    Arreglos sk = new Arreglos();
    while(i.hasNext()){
    	Arreglos at;
      Object o = i.next();
      try{
        at = (Arreglos)o;
      }
      catch(ClassCastException cce){
        at = new Arreglos(o);
      }
      Arreglos clos = at.closure(deps);
      if(clos.equiv(this)){
        sk.add(at);
      }
    }

    return sk;
  }

  public Arreglos candKeys(Funciones deps){
	  Arreglos sk = this.bruteKeys(deps);
	  Arreglos cand = new Arreglos();
    Iterator i = sk.iterator();
    boolean flag;
    while(i.hasNext()){
    	Arreglos a = (Arreglos)i.next();
    	Arreglos c = a.reduction();
      Iterator j = c.iterator();
      flag = true;
      while(j.hasNext()){
    	  Arreglos d = ((Arreglos)j.next());
        if(d.closure(deps).equiv(this))
          flag = false;
      }
      if(flag == true)
        cand.add(a);

    }

    return cand;
  }

  public Arreglos missing(Funciones deps){
  //present in att. set but not in fun.dep.
	  Arreglos allDeps = deps.particles();
	  Arreglos inte = allDeps.intersect(this);
	  Arreglos ret = this.minus(inte);
    return ret;
  }

  public boolean isSuperKey(Arreglos relation, Funciones deps){

	  Arreglos sks = relation.superKeys(deps);
    if(sks.contains(this))
      return true;
    else
      return false;
  }

  public boolean isPrime(Arreglos relation, Funciones deps){
    boolean flag = false;
    Arreglos ccs = relation.candKeys(deps);
    Iterator i = ccs.iterator();
    while(i.hasNext()){
    	Arreglos c = (Arreglos)i.next();
      if(c.includes(this))
        flag = true;
    }
    return flag;
  }

  public boolean isFNBC(Funciones deps){
    boolean flag = true;
    Iterator i = deps.iterator();
      while(i.hasNext()){
        Dependencias fd = (Dependencias)i.next();
        if(fd.failsFNBC(this, deps))
          flag = false;
      }
    return flag;
  }

  public boolean is3FN(Funciones deps){
    boolean flag = true;
    Iterator i = deps.iterator();
      while(i.hasNext()){
        Dependencias fd = (Dependencias)i.next();
        if(fd.fails3FN(this, deps))
          flag = false;
      }
    return flag;
  }

  public boolean is2FN(Funciones deps){
    //added to speed up exec
    //Every relation that is in 1FN, with a single attribute key, is automatically in 2NF
    if(this.ckSize(deps) < 2)
      return true;

    boolean flag = true;
    Iterator i = deps.iterator();
      while(i.hasNext()){
        Dependencias fd = (Dependencias)i.next();
        if(fd.fails2FN(this, deps))
          flag = false;
      }
    return flag;
  }

  public String getNormalForm(Funciones deps){
    if(this.isFNBC(deps))
      return "FNBC";
    else
      if(this.is3FN(deps))
        return "3FN";
      else
        if(this.is2FN(deps))
          return "2FN";
        else
          return "1FN";
  }

  public int ckSize(Funciones deps){
    //returns the size (in attributes) of the biggest candidate key
    int ret = 0;
    Arreglos ccs = this.candKeys(deps);
    Iterator i = ccs.iterator();
    while(i.hasNext()){
      Arreglos c = (Arreglos)i.next();
      if(c.size() > ret)
        ret = c.size();
    }
    return ret;
  }

  public Funciones depLoss(Funciones dfs, Arreglos pr){
    Funciones ret = new Funciones();
    Object vec[] = pr.toArray();
    Iterator it = dfs.iterator();
    while(it.hasNext()){
      Dependencias f = (Dependencias)it.next();
      Arreglos z = f.giveX();
      Arreglos temp = new Arreglos();
      while(!z.equiv(temp)){
        temp = z.copy();
        for(int i=0;i<pr.size();i++){
        	Arreglos cl = z.intersect((Arreglos)vec[i]).closure(dfs);
          z = z.unionSafe(cl.intersect((Arreglos)vec[i]));
        }
      }
      if(!z.includes(f.giveY()))
        ret.add(f);
    }

    return ret;
  }


  public boolean depLoss(Dependencias fd, Funciones dfs, Arreglos pr){
    //Funciones ret = new Funciones();
    boolean ret = true;
    Object vec[] = pr.toArray();

      Dependencias f = fd;
      Arreglos z = f.giveX();
      Arreglos temp = new Arreglos();
      while(!z.equiv(temp)){
        temp = z.copy();
        for(int i=0;i<pr.size();i++){
        	Arreglos cl = z.intersect((Arreglos)vec[i]).closure(dfs);
          z = z.unionSafe(cl.intersect((Arreglos)vec[i]));
        }
      }
      if(!z.includes(f.giveY()))
        ret = false;


    return ret;
  }
/*
  public boolean infoLoss(Funciones dfs, Arreglos pr){
    //no info loss
    boolean tab = true;
    Tableaux m = new Tableaux(this, pr);
    if(!m.complete()){
      if(m.test(dfs))
        tab = false;
    }
    else
      tab = false;
    return tab;
  }
  */ /*
  public boolean isEficient(Funciones dfs, Arreglos pr){
  //returns true if db design is eficient
  //ie there's no df loss nor info loss
    //boolean tab = false;
    boolean infoLost = this.infoLoss(dfs, pr);
    boolean depLost;
    //no info loss
    /*Tableaux m = new Tableaux(this, pr);
    if(!m.complete())
      if(m.test(dfs))
        tab = true;
    else
      tab = true;
    //no dep loss
    Funciones dp = this.depLoss(dfs, pr);
    depLost = !dp.isEmpty();
    return (!infoLost)&&(!depLost);
  } */

  public Funciones getRelevantDeps(Funciones dfs){
  //"this" is a single projection
  //uses F+ & fminStep1 !!!!!
    Funciones ret = new Funciones();
    Funciones dfsPlus = dfs.getFPlus().fmin1();
    Iterator i = dfsPlus.iterator();
    while(i.hasNext()){
      Dependencias f = (Dependencias)i.next();
      if(f.isRelevant(this)){
        ret.add(f);
      }
    }

    return ret;
  }

  public String toFPlusFormat(){
    String s = "";
    for(int i = 0;i<this.size();i++){
      s = s + this.elementAt(i).toString();
      if(i<(this.size()-1))
        s = s + ",";
    }
    //s = s + ";";
    return s;
  }

  public String toHumanFormat(){
    String s = "(";
    for(int i = 0;i<this.size();i++){
      s = s + this.elementAt(i).toString();
      if(i<(this.size()-1))
        s = s + ",";
    }
    s = s + ")";
    return s;
  }

  public Arreglos atomize(){
	  Arreglos ret = new Arreglos();
    Iterator i = this.iterator();
    while(i.hasNext()){
      Object o = i.next();
      String st = o.getClass().toString();
      if(st.endsWith("Arreglos")){
        ret = ret.unionSafe(((Arreglos)o).atomize());
      }
      else
        ret.add(o);
    }

    return ret;
  }

}
