package com.esgi.googlenews.Modeles;

/**
 * Flag object
 */
public class Flag
{
    private int id;
    private String name;

    /**
     *
     * @param id
     * @param name
     */
    public Flag (int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    /**
     * Get Name
     *
     * @return name string
     */
    public String getName ()
    {
        return name;
    }

    /**
     * Set Name
     *
     * @param name string
     */
    public void setName (String name)
    {
        this.name = name;
    }

    /**
     * Get id
     *
     * @return id int
     */
    public int getId ()
    {
        return id;
    }

    /**
     * Set id
     *
     * @param id int
     */
    public void setId (int id)
    {
        this.id = id;
    }

    /**
     * toString
     *
     * @return Tag name string
     */
    @Override
    public String toString ()
    {
        return name;
    }
}
