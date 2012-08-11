#pragma once

#include "GL_CheckerBoard.h"

#include <Windows.h>

#include <stdio.h>
#include <vector> 
#include <tchar.h>
#include <iostream>
#include <strstream>
#include <math.h>
#include <iomanip>
#include <gl\GLU.h>
#include <glut-3.7.6-bin\glut.h>

#include "GameUtilities.h"

// ----------------------------------------------------------
// Global Variables
// ----------------------------------------------------------

OnSlotClickedListener* listener;
std::vector<std::vector<GamePiece>>* grid;

Slot cursorPosition = Slot(0, 0);

// My definition for a Solid Cylinder
#define MyOwnSolidCylinder(QUAD, BASE, TOP, HEIGHT, SLICES, STACKS) \
gluCylinder(QUAD, BASE, TOP, HEIGHT, SLICES, STACKS); \
glRotatef(180, 1,0,0); \
gluDisk(QUAD, 0.0f, BASE, SLICES, 1); \
glRotatef(180, 1,0,0); \
glTranslatef(0.0f, 0.0f, HEIGHT); \
gluDisk(QUAD, 0.0f, TOP, SLICES, 1); \
glTranslatef(0.0f, 0.0f, -HEIGHT);

// Initial size of the window
int window_width = 1440;
int window_height = 900;

// Camera Coordinates
double cam_x = 0.0;
double cam_y = 3.0; // 3.0
double cam_z = 15.0;

// Translates the light source
double light_x = 0.0;
double light_y = 5.0;

// Position the light at the origin
const GLfloat light_pos[] = { 0.0, 0.0, 0.0, 1.0 };

// Vectors for material colors and properties
// Piece Colors

GLfloat is_dead = 1.0; // Alpha value, controls transparency. Setting this to 0.0 renders the object invisible, setting it to 1.0 renders it opaque

GLfloat red_piece[] = { 0.8f, 0.0, 0.0, is_dead }; // This controls the color and alpha value for all objects in the scene to which it is applied; Changing these values affects all objects that are red
GLfloat white_piece[] = { 1.0, 1.0, 1.0, is_dead };

// Board Colors
const GLfloat beige[] = { 1.0, 0.8078431372549019607843137254902, 0.61960784313725490196078431372549, 1.0 };
const GLfloat brown[] = { 0.81960784313725490196078431372549, 0.54509803921568627450980392156863, 0.27843137254901960784313725490196, 1.0 };
const GLfloat blue[] = { 0.1686, 0.1098, 0.8392, 1.0 };

// Lighting and Shading Colors
const GLfloat white[] = { 1.0, 1.0, 1.0, 1.0 };
const GLfloat polished[] = { 100.0 };
const GLfloat dull[] = { 0.0 };

void resize_window (int w, int h) 
{
	// Allow window to be resized without losing the model
	window_width = w;
	window_height = h;

	// Use entire window to display the model
	glViewport(0, 0, window_width, window_height);

	// Adjust aspect-ratio to match new window dimensions
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluPerspective(35.0, GLfloat(window_width)/GLfloat(window_height), 1.0, 50.0);
	glutPostRedisplay();
}

void draw_light ()
{
	// Position the light and show where it is, representing it as a sphere
	glPushMatrix();
		glTranslatef(light_x, light_y, 5.0);
		glLightf(GL_LIGHT0, GL_CONSTANT_ATTENUATION, 1.0);
		glLightfv(GL_LIGHT0, GL_POSITION, light_pos);
		glDisable(GL_LIGHTING);
		glColor3d(0.9, 0.9, 0.5);
		glutSolidSphere(0.1, 40, 40);
		glEnable(GL_LIGHTING);
	glPopMatrix();
}

void draw_board ()
{
	// Draw 64 squares on the XY plane, 32 brown and 32 beige
	glPushMatrix();
		glTranslatef(-4.0, -2.0, -4.0);
		glRotatef(90.0, 1.0, 0.0, 0.0);
		for (GLint i = 0; i < 8; i++)
		{
			for (GLint j = 0; j < 8; j++)
			{
				if (i == ::cursorPosition.y && j == ::cursorPosition.x)
				{
					glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, blue);
					glMaterialfv(GL_FRONT, GL_SPECULAR, white);
					glMaterialfv(GL_FRONT, GL_SHININESS, polished);
				}
				else if((i + j)%2 == 0) // if i + j is even, color the square brown
				{
					glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, brown);
					glMaterialfv(GL_FRONT, GL_SPECULAR, white);
					glMaterialfv(GL_FRONT, GL_SHININESS, polished);
				}
				else // color it beige
				{
					glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, beige);
					glMaterialfv(GL_FRONT, GL_SPECULAR, white);
					glMaterialfv(GL_FRONT, GL_SHININESS, polished);
				}
				glRecti(i, j, (i+1), (j+1));    // draws the square
			}
		}
	glPopMatrix();
}

// Function for drawing a red piece
void draw_red_piece (int x, int y)
{
	glPushMatrix();
		glTranslatef(x, y, -0.25);
		glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, red_piece);
		glMaterialfv(GL_FRONT, GL_SPECULAR, white);
		glMaterialfv(GL_FRONT, GL_SHININESS, polished);

		GLUquadric *quadric = gluNewQuadric();
		gluQuadricDrawStyle(quadric, GLU_FILL);
		gluQuadricOrientation(quadric, GLU_OUTSIDE);
		MyOwnSolidCylinder(quadric, 0.4f, 0.4f, 0.125f, 20, 20);
		gluDeleteQuadric(quadric);
	glPopMatrix();
}

// Function for drawing a white piece
void draw_white_piece (int x, int y)
{
	glPushMatrix();
		glTranslatef(x, y, -0.25);
		glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, white_piece);
		glMaterialfv(GL_FRONT, GL_SPECULAR, white);
		glMaterialfv(GL_FRONT, GL_SHININESS, polished);

		GLUquadric *quadric = gluNewQuadric();
		gluQuadricDrawStyle(quadric, GLU_FILL);
		gluQuadricOrientation(quadric, GLU_OUTSIDE);
		MyOwnSolidCylinder(quadric, 0.4f, 0.4f, 0.125f, 20, 20);
		gluDeleteQuadric(quadric);
	glPopMatrix();
}

// Function for drawing a red king
void draw_red_king (int x, int y)
{
	glPushMatrix();
		glTranslatef(x, y, -0.35);
		glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, red_piece);
		glMaterialfv(GL_FRONT, GL_SPECULAR, white);
		glMaterialfv(GL_FRONT, GL_SHININESS, polished);

		GLUquadric *quadric = gluNewQuadric();
		gluQuadricDrawStyle(quadric, GLU_FILL);
		gluQuadricOrientation(quadric, GLU_OUTSIDE);
		MyOwnSolidCylinder(quadric, 0.4f, 0.4f, 0.25f, 20, 20);
		gluDeleteQuadric(quadric);
	glPopMatrix();
}

// Function for drawing a white king
void draw_white_king (int x, int y)
{
	glPushMatrix();
		glTranslatef(x, y, -0.35);
		glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, white_piece);
		glMaterialfv(GL_FRONT, GL_SPECULAR, white);
		glMaterialfv(GL_FRONT, GL_SHININESS, polished);

		GLUquadric *quadric = gluNewQuadric();
		gluQuadricDrawStyle(quadric, GLU_FILL);
		gluQuadricOrientation(quadric, GLU_OUTSIDE);
		MyOwnSolidCylinder(quadric, 0.4f, 0.4f, 0.25f, 20, 20);
		gluDeleteQuadric(quadric);
	glPopMatrix();
}

// Sets the pieces at their starting positions on the board
void set_pieces ()
{
	glPushMatrix();
		glTranslatef(-3.5, -2.120, -3.5);
		glRotatef(90.0, 1.0, 0.0, 0.0);
		/*for (GLint i = 0; i < 8; i++)
		{
			for(GLint j = 0; j < 8; j++)
			{
				if(j%2 == 0 && i == 0 || j%2 == 0 && i == 2 || j%2 != 0 && i == 1)
					draw_white_piece(i, j); // Positions the white pieces
				else if(j%2 != 0 && i == 5 || j%2 != 0 && i == 7 || j%2 == 0 && i == 6)
					draw_red_piece(i, j); // Positions the red pieces
			}
		}*/
		std::vector<std::vector<GamePiece>>* grida=grid;
		for (int i = 0; i < 8; i++) 
		{ 
			for (int j = 0; j < 8; j++) 
			{ 
				switch(::grid->at(i).at(j))
				{
					case RED_PIECE:
						draw_red_piece (i,j);
						break;
					case WHITE_PIECE:
						draw_white_piece (i,j);
						break;
					case RED_KING_PIECE:
						draw_red_king (i,j);
						break;
					case WHITE_KING_PIECE:
						draw_white_king (i,j);
						break;
				}
			}
		 } 
	glPopMatrix();
}

// Enables Function keys for input
void special_keys (int key, int x, int y)
{
	switch (key)
	{
	case GLUT_KEY_F1:
		light_x = 0.0;
		cam_y = 3.0;
		cam_z = 15.0;
		break;

	case GLUT_KEY_F2:
		if (light_x == 0.0)
			light_x = 5.0;
		else if (light_x == 5.0)
			light_x = -5.0;
		else if (light_x == -5.0)
			light_x = 5.0;
		break;
	case GLUT_KEY_F3:
		cam_y = 3.0;
		cam_z = -15.0;
		break;

	case GLUT_KEY_F4:
		cam_y = 12.0;
		cam_z = -0.1;
		break;

	case GLUT_KEY_LEFT:
		::cursorPosition.x = (cursorPosition.x > 0) ? cursorPosition.x-1 : 0; 
		break;

	case GLUT_KEY_UP:
		::cursorPosition.y = (cursorPosition.y < 7) ? cursorPosition.y+1 : 7;
		break;

	case GLUT_KEY_RIGHT:
		::cursorPosition.x = (cursorPosition.x < 7) ? cursorPosition.x+1 : 7;
		break;

	case GLUT_KEY_DOWN:
		::cursorPosition.y = (cursorPosition.y > 0) ? cursorPosition.y-1 : 0;
		break;

	case GLUT_KEY_F5 :
		::listener->onSlotClicked(::cursorPosition.x, ::cursorPosition.y);
		break;

	case GLUT_KEY_F12:
		std::exit(0);
		break;
	}
		
	//  Request display update
	glutPostRedisplay();
}

void animate()
{
	glutPostRedisplay();
}



void display () 
{
	using std::vector;

	// Display callback function.
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();

	gluLookAt(cam_x, cam_y, cam_z, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0); // Sets up the camera

	glRotatef(90.0, 0.0, 1.0, 0.0); // Rotates the scene

	draw_light(); // Sets up the light source
	draw_board(); // Draws the board

	set_pieces(); // Positions the pieces

	glutSwapBuffers();
	glFlush();
}

int init_view (int argc, char *argv[]) 
{

	// Initialize OpenGL and enter loop
	glutInit(&argc, argv);
	glutInitDisplayMode (GLUT_RGBA | GLUT_DOUBLE | GLUT_DEPTH);
	glutInitWindowSize(window_width, window_height);
	glutCreateWindow("Checker Board");

	glClearColor(0.0, 0.0, 0.0, 1.0);

	// Initialize the light
	glEnable(GL_LIGHTING);
	glEnable(GL_LIGHT0);
	glLightfv(GL_LIGHT0, GL_DIFFUSE, white);
	glLightfv(GL_LIGHT0, GL_SPECULAR, white);

	// Request hidden surface elimination and register callbacks
	glEnable(GL_DEPTH_TEST);

	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	glEnable( GL_BLEND );

	glutDisplayFunc(display);
	glutSpecialFunc(special_keys); // Enables Function keys to be used for input
	glutIdleFunc(animate);
	glutReshapeFunc(resize_window);

	glutMainLoop();
	return 0;
}

