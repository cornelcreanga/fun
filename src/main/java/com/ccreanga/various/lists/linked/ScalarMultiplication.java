package com.ccreanga.various.lists.linked;

import org.apache.commons.math3.util.Pair;


public class ScalarMultiplication {

    public static SingleLinkedList multiply(
            SingleLinkedList<Pair<Integer,Integer>> list1,
            SingleLinkedList<Pair<Integer,Integer>> list2){

        SingleLinkedList<Pair<Integer,Integer>> product = new SingleLinkedList<>();
        Node<Pair<Integer,Integer>> first = list1.getHead();
        Node<Pair<Integer,Integer>> second = list2.getHead();

        while(first!=null && second!=null){

            Integer index1 = first.getData().getKey();
            Integer index2 = second.getData().getKey();


            if (index1.equals(index2)){
                product.add(new Pair<>(index1,first.getData().getValue()*second.getData().getValue()));
                first = first.next;
                second = second.next;
            }

            if (index1.compareTo(index2)<0){
                while ( (first!=null) &&  first.getData().getKey()<second.getData().getKey())
                    first = first.next;
            }else{
                while( (second!=null) &&  first.getData().getKey()>second.getData().getKey())
                    second = second.next;
            }

        }

        return product;
    }


    public static void main(String[] args) {
        SingleLinkedList<Pair<Integer,Integer>> list1 = new SingleLinkedList<>();
        list1.add(new Pair<>(1,4));
        list1.add(new Pair<>(5,2));
        list1.add(new Pair<>(7,2));
        list1.add(new Pair<>(9,2));

        SingleLinkedList<Pair<Integer,Integer>> list2 = new SingleLinkedList<>();
        list2.add(new Pair<>(2,4));
        list2.add(new Pair<>(3,4));
        list2.add(new Pair<>(4,4));
        list2.add(new Pair<>(5,4));
        list2.add(new Pair<>(7,6));

        SingleLinkedList<Pair<Integer,Integer>> product = ScalarMultiplication.multiply(list1,list2);
        product.iterate(node -> System.out.println(node.getData().getKey()+" - "+node.getData().getSecond()));
    }
}
