package Operaciones;

import java.util.Iterator;

public class Funciones extends Arreglos {

  public Funciones fmin1(){
    //turn rightmost attributes into single ones (FMIN STEP 1)

      Funciones n =  new Funciones();
      Iterator i = this.iterator();
      while(i.hasNext()){
        Dependencias f = (Dependencias)i.next();
        if(f.giveY().size() > 1){
          for(int j = 0; j < f.giveY().size(); j++){
            Object o = f.giveY().elementAt(j);
            Arreglos a = new Arreglos();
            a.add(o);
            Dependencias c = new Dependencias(f.giveX(), a);
            n.add(c);
          }
        }
        else{
          n.add(f);
        }
      }
      return n;


  }

  public Funciones fmin2(){
    //eliminate redundant attributes on the left (FMIN STEP 2)
      Iterator i = this.iterator();
      Funciones ret = new Funciones();
      //traverse each dep.
      while(i.hasNext()){
        Dependencias fd = (Dependencias)i.next();

        Arreglos cs = fd.giveX();
        if(cs.size()>1){
          cs = cs.reduction();
          //ch are the candidate att.
          Arreglos ch = new Arreglos();//cs contains right side subsets
          Iterator i2 = cs.iterator();
          //find early att. closures
          while(i2.hasNext()){
            Arreglos n = (Arreglos)i2.next();
            Arreglos tmp = n.copy();
            if(tmp.closure(this).includes(fd.giveY()))
              ch.add(n);
          }
          //now get the smaller candidate
          if(ch.size() == 0){//or keep the fun. dep.
            ret.add(fd);
          }
          else{
            Arreglos win = ch.min();
            Dependencias w = new Dependencias(win,fd.giveY());
            ret.add(w);
          }
        }
        else{
          ret.add(fd);
        }
      }
      //Funciones ret = (Funciones)this.copy();
      return ret;

  }

  public Funciones chop(){
    //eliminate redundant dependencies           (FMIN STEP 3)
      Iterator i = this.iterator();
      Funciones ret = new Funciones();
      Funciones dec = this.copie();
      //traverse each dep.
      while(i.hasNext()){
        Dependencias fd = (Dependencias)i.next();
        dec = dec.remove(fd);
        Arreglos tma = fd.giveX().copy();
        Arreglos clo = tma.closure(dec);
        if(!clo.includes(fd.giveY()))
          ret.add(fd);
      }

      return ret;

  }

  public Funciones fmin3(){
    //eliminate redundant dependencies           (FMIN STEP 3)
      Iterator i = this.iterator();
      Funciones ret = new Funciones();
      Funciones dp = this.copie();
      //traverse each dep.
      while(i.hasNext()){
        Dependencias fd = (Dependencias)i.next();
        Funciones tmp = dp.remove(fd);
        Arreglos tma = fd.giveX().copy();
        Arreglos clo = tma.closure(tmp);
        if(!clo.includes(fd.giveY()))
          ret.add(fd);
        else
          dp = tmp;

      }

      return ret;

  }

  public Funciones copie(){
    Funciones ret = new Funciones();
    Iterator i = this.iterator();
    while(i.hasNext()){
      Dependencias f = (Dependencias)i.next();
      ret.add(f.copy());
    }
    return ret;
  }

  public String toString(){
    return DependenciasFuncionales.toString();
  }

  public Funciones remove(Dependencias fd){
  //returns a new chopped Funciones
    Iterator i = this.iterator();
    Funciones c = new Funciones();
    while(i.hasNext()){
      Dependencias t = (Dependencias)i.next();
      if(!fd.equiv(t))
        c.add(t);
    }
    return c;

  }

  public Arreglos leftParticles(){
    //turns this left side dep set into single attributes
    Iterator i = this.iterator();
    Arreglos ret = new Arreglos();
    while(i.hasNext()){
      Dependencias f = (Dependencias)i.next();
      Arreglos cs = f.giveX();
      Iterator j = cs.iterator();
      while(j.hasNext()){
        ret.add(j.next());
      }
    }
    return ret;
  }

  public Arreglos rightParticles(){
    //turns this right side dep set into single attributes
    Iterator i = this.iterator();
    Arreglos ret = new Arreglos();
    while(i.hasNext()){
      Dependencias f = (Dependencias)i.next();
      Arreglos cs = f.giveY();
      Iterator j = cs.iterator();
      while(j.hasNext()){
        ret.add(j.next());
      }
    }
    return ret;
  }

  public Arreglos particles(){
    //dep set into single attributes
    Arreglos r = this.rightParticles();
    Arreglos l = this.leftParticles();
    return r.union(l);
  }

  public Arreglos yAts(){
    //turns this right side dep set into multiple attributes
    Iterator i = this.iterator();
    Arreglos ret = new Arreglos();
    while(i.hasNext()){
      Dependencias f = (Dependencias)i.next();
      ret.add(f.giveY());
    }
    return ret;
  }

  public Arreglos xAts(){
    //turns this left side dep set into multiple attributes
    Iterator i = this.iterator();
    Arreglos ret = new Arreglos();
    while(i.hasNext()){
      Dependencias f = (Dependencias)i.next();
      ret.add(f.giveX());
    }
    return ret;
  }

  public Arreglos allAts(){
    return this.xAts().union(this.yAts());
  }


  public Funciones getFPlus(){
    //clausurar cada atributo de la relacion y
    //generar dependencias que determinen cada atributo de la clausura salvo el
    //mismo atributo.
    //y como genero deps con li compuesto? (brute force?)

    Funciones ret = new Funciones();
    Arreglos all = this.allAts().atomize();
    all = all.reduction();
    Iterator i = all.iterator();
    while(i.hasNext()){
      Arreglos x = (Arreglos)i.next();
      Arreglos y = x.closure(this).minus(x);
      if(y.size()>0){
        Dependencias fd = new Dependencias(x, y);
        ret.add(fd);
      }
    }

    return ret;
  }

  public String toFPlusFormat(){
    String s = "";
    Iterator i = this.iterator();
    while(i.hasNext()){
      Dependencias fd = (Dependencias)i.next();
      s = s + fd.giveX().toFPlusFormat() + ">" + fd.giveY().toFPlusFormat() + ";\n";
    }

    return s;
  }

  public String toHumanFormat(){
    String s = "";
    Iterator i = this.iterator();
    while(i.hasNext()){
      Dependencias fd = (Dependencias)i.next();
      s = s + fd.toHumanFormat() + "\n";
    }

    return s;
  }

  public Funciones fmin(){
    Funciones dp = this.copie();
    return dp.fmin1().fmin2().fmin3();
  }

  public boolean equiv(Funciones dp){
    //this+ = dp+
    boolean flag = true;
    //si x->y incluido_en L => x->y incluido_en M+ (o y incluido_en x+ de M)
    Iterator l = this.iterator();
    while(l.hasNext()){
      Dependencias fdl = (Dependencias)l.next();
      if(!dp.includesPlus(fdl))
        flag = false;
    }
    if(flag){
      //si z->w incluido_en M => z->w incluido_en L+ (o w incluido_en z+ de L)
      Iterator m = dp.iterator();
      while(m.hasNext()){
        Dependencias fdm = (Dependencias)m.next();
        if(!this.includesPlus(fdm))
          flag = false;
      }
    }

    return flag;
  }

  public boolean includesPlus(Dependencias fd){
    //true if fd is included in this+
    //fd: x->y
    //true si x+ de this incluye a y
    return fd.giveX().closure(this).includes(fd.giveY());
  }

  public boolean includes(Dependencias fd){
    boolean flag = false;
    Iterator i = this.iterator();
    while(i.hasNext()){
      Dependencias f = (Dependencias)i.next();
      if(fd.equiv(f))
        flag = true;
    }
    return flag;
  }

  public Dependencias getGlobalDep(Arreglos rel){
    Dependencias ret = null;
    Iterator i = this.iterator();
    while(i.hasNext()){
      Dependencias fd = (Dependencias)i.next();
      if(fd.toRelation().equiv(rel)){
        ret = fd;
        break;
      }
    }
    return ret;
  }

}