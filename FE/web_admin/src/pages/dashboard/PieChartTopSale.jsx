import { PieChart, Pie, Cell, Tooltip, Legend, ResponsiveContainer } from 'recharts';

const PieChartTopSale = ({
  categories,
  products
}) => {
  const data = categories.map(category => ({
    category: category.category,
    quantity: 0,
  }));

  categories?.forEach(category => {
    data.category = category.category
  })

  products.forEach(product => {
    const catName = product.category?.category;
    const match = data.find(r => r.category === catName);
    if (match) {
      match.quantity += product.salesCount || 0;
    }
  });

  const dataPie = data.map(item => ({
    name: item.category,
    value: item.quantity,
  }));

  const generateColors = (num) => {
    const colors = new Set();
    while (colors.size < num) {
      const color = `#${Math.floor(Math.random() * 16777215).toString(16).padStart(6, '0')}`; // Tạo màu HEX
      colors.add(color);
    }
    return Array.from(colors);
  };

  const COLORS = generateColors(dataPie.length);

  return (
    <div className="pie-chart">
      <h3 className='pie-chart__label'>Danh mục bán chạy</h3>
      <ResponsiveContainer width={'100%'} height={'100%'}>
          <PieChart>
            <Pie
              data={dataPie}
              cx="50%"
              cy="50%"
              innerRadius={80}
              outerRadius={120}
              fill="#8884d8"
              paddingAngle={6}
              dataKey="value"
            >
              {dataPie.map((entry, index) => (
                <Cell 
                  key={`cell-${index}`} 
                  fill={COLORS[index % COLORS.length]} 
                  style={{cursor: 'pointer'}}
                />
              ))}
            </Pie>
            <Tooltip formatter={(value, name) => [`${value} lượt bán`, `${name}`]} />
            <Legend />
          </PieChart>
        </ResponsiveContainer>
    </div>
  );
};

export default PieChartTopSale;
