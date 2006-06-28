/*
 * Copyright 2005-2006 Noelios Consulting.
 *
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the "License").  You may not use this file except
 * in compliance with the License.
 *
 * You can obtain a copy of the license at
 * http://www.opensource.org/licenses/cddl1.txt
 * See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * HEADER in each file and include the License file at
 * http://www.opensource.org/licenses/cddl1.txt
 * If applicable, add the following below this CDDL
 * HEADER, with the fields enclosed by brackets "[]"
 * replaced with your own identifying information:
 * Portions Copyright [yyyy] [name of copyright owner]
 */

package com.noelios.restlet.build;

/**
 * Fluent builder for any object.
 * @author Jerome Louvel (contact[at]noelios.com) <a href="http://www.noelios.com/">Noelios Consulting</a>
 */
public class ObjectBuilder
{
	/** The parent builder. */
	protected ObjectBuilder parent;
	
	/** The wrapped node. */
	protected Object node;
	
	/**
	 * Constructor.
	 * @param parent The parent builder.
	 * @param node The wrapped node.
	 */
	public ObjectBuilder(ObjectBuilder parent, Object node)
	{
		this.parent = parent;
		this.node = node;
	}
	
   /**
    * Returns the node wrapped by the builder.
    * @return The node wrapped by the builder.
    */
	public Object getNode()
	{
		return this.node;
	}

	/**
	 * Go up one level in the builders tree.
	 * @return The parent builder.
	 */
	public ObjectBuilder up()
	{
		return parent;
	}

	/**
	 * Go up several levels in the builders tree.
	 * @return The ancestor builder.
	 */
	public ObjectBuilder up(int levels)
	{
		ObjectBuilder result = parent;
		for(int i = 0; (result != null) && (i < levels - 1); i++)
		{
			result = result.up();
		}
		
		return result;
	}

	/**
	 * Go up to the first parent router builder.
	 * @return The parent router builder.
	 */
	public RouterBuilder upRouter()
	{
		RouterBuilder result = null;
		ObjectBuilder current = this;

		for(boolean goUp = true; (result == null) && goUp; )
		{
			goUp = (current.up() != null);
			if(goUp) current = current.up();
			if(current instanceof RouterBuilder) result = (RouterBuilder)current;
		}
		
		return result;
	}

	/**
	 * Go up to a specified ancestor router builder.
	 * @param level The ancestor level (1 = first parent)
	 * @return The parent router builder.
	 */
	public RouterBuilder upRouter(int level)
	{
		RouterBuilder result = null;

		for(int i = 0; i < level; i++)
		{
			if(result == null) 
			{
				result = upRouter();
			}
			else
			{
				result = result.upRouter();
			}
		}
		
		return result;
	}

	/**
	 * Go to the root of the builders tree.
	 * @return The root builder.
	 */
	public ObjectBuilder root()
	{
		ObjectBuilder result = this;

		for(boolean goUp = true; goUp; )
		{
			goUp = (result.up() != null);
			if(goUp) result = result.up();
		}
		
		return result;
	}

	/**
	 * Casts the current builder. 
	 * @return A router builder.
	 */
	public RouterBuilder toRouter()
	{
		return (RouterBuilder)this;
	}

	/**
	 * Casts the current builder. 
	 * @return A Filter builder.
	 */
	public FilterBuilder toFilter()
	{
		return (FilterBuilder)this;
	}

	/**
	 * Casts the current builder. 
	 * @return A Component builder.
	 */
	public ComponentBuilder toComponent()
	{
		return (ComponentBuilder)this;
	}

}
