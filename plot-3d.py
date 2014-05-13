from matplotlib import pyplot
from matplotlib import animation
from mpl_toolkits.mplot3d import Axes3D
from mpl_toolkits.mplot3d.art3d import Line3DCollection
import numpy
import math

cycles = 1000
scale = 40

def coordinates_filename(i):
    return './vivaldi-tmp/coordinates' + `i` + '.csv'
def meta_filename(i):
    return './vivaldi-tmp/meta' + `i` + '.csv'

def id_to_marker(i):
    markers = {
        0 : 'o', # circle
        1 : 'D', # diamond
        2 : 's', # square
        3 : '^' # triangle_up}
    }
    return markers[i % len(markers)]

fig = pyplot.figure()
#ax = pyplot.axes(projection='3d', xlim=(-scale, scale), ylim=(-scale, scale))
ax = fig.add_subplot(111, projection='3d', xlim=(-scale, scale), ylim=(-scale, scale), zlim=(0, scale))
sc = ax.scatter3D([], [])
li = Line3DCollection([], colors=(0,0,0))

# text_cycle = pyplot.text(-0.9*scale, 0.9*scale, "Cycle: ")
# text_sum = pyplot.text(-0.9*scale, 0.85*scale, "Sum of errors: ")
# text_avg_err = pyplot.text(-0.9*scale, 0.8*scale, "Average error: ")

# text_avg_uncertainty = pyplot.text(-0.9*scale, -0.8*scale, "Average uncertainty: ")
# text_avg_uncertainty_balance = pyplot.text(-0.9*scale, -0.85*scale, "Average uncertainty balance: ")
# text_avg_move_distance = pyplot.text(-0.9*scale, -0.9*scale, "Average move distance: ")


def init():
    sc = ax.scatter([],[])
    return sc

def normalize(x):
    return x/30

def randrange(n, vmin, vmax):
    return (vmax-vmin)*numpy.random.rand(n) + vmin

def animate(i):
    global sc
    global li
    global ax
    file = open ( coordinates_filename(i) , 'r' )
    # Fetch a list of coordinates [[x1,y1], [x2,y2] ...]
    coords = [ map( float, line.split(',') ) for line in file ]
    #coords = [[0,0,0],[1,1,1]]
    file.close()
    # Separate into two lists [x1, x2 ...] and [y1, y2 ...]
    [x, y, z, c] = map( list, zip( *coords ) )
    m = map(id_to_marker, c)
    #z = [1]*len(x)
    # sc._offsets3d = [x, y, z]
    # sc.set_color(numpy.array([0.1, 0.3, 0.5, 0.7, 0.9]*200))
    # c = [0.1, 0.3, 0.5, 0.7, 0.9]*200
    # pyplot.cla()
    
    sc.remove()
    sc = ax.scatter3D(x, y, zs=z, c=c)
    e = 10 + 20 + 20 * math.cos(float(i)/30)
    ax.view_init(elev=e, azim=i)

    #li.set_segments([((0,0,0),(0,0,20))])
    #ax.add_collection(li)


    # text_cycle.set_text("Cycle: " + `i`)
    file = open ( meta_filename(i) , 'r' )
    meta = [ line.split(':') for line in file ]
    # text_sum.set_text("Sum of errors: " + `float(meta[0][1])`)
    # text_avg_err.set_text("Average error: " + `float(meta[1][1])`)
    # text_avg_uncertainty.set_text("Average uncertainty: " + `float(meta[2][1])`)
    # text_avg_uncertainty_balance.set_text("Average uncertainty balance: " + `float(meta[3][1])`)
    # text_avg_move_distance.set_text("Average move distance: " + `float(meta[4][1])`)
    file.close()

anim = animation.FuncAnimation(fig, animate, xrange(cycles), init_func=init, interval=10, repeat=False)

pyplot.show()

# 16:9 / 1920*1080 -> 16*9 inches * 120 dpi
fig.set_size_inches(16,9)
# anim.save('basic_animation.mp4', extra_args=['-vcodec', 'libx264'], dpi=120, fps=25)

"""
anim.save('basic_animation.mp4', fps=1, extra_args=['-vcodec', 'libx264'])
pyplot.ylabel('some numbers')
pyplot.savefig('test.pdf')

c = numpy.random.random(9)
sc.set_color(c)

"""